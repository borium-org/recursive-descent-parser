package org.borium.rdp;

import static org.borium.rdp.Text.*;

public class Symbol
{
	private static SymbolTable symbol_tables = null;

	public static Symbol symbol_insert_symbol(SymbolTable table, Symbol symbol)
	{
		Symbol s = symbol;

		s.hash = table.hash(table.hash_prime, text_get_string(symbol.id));
		int hash_index = s.hash % table.hash_size;

		s.next_hash = table.table[hash_index];
		table.table[hash_index] = s;

		s.last_hash.set(table.table[hash_index]);

		// if this wasn't the start of a new list ...
		if (s.next_hash != null)
		{
			// ...point old list next back at s
			s.next_hash.last_hash.set(s.next_hash);
		}

		// now insert in scope list
		s.next_scope = table.current.next_scope;
		table.current.next_scope = s;

		// set up pointer to scope block
		s.scope = table.current;

		return symbol;
	}

	public static SymbolTable symbol_new_table(String name, int symbol_hashsize, int symbol_hashprime,
			CompareHashPrint compareHashPrint)
	{
		SymbolTable temp = new SymbolTable();
		SymbolScopeData scope = new SymbolScopeData();
		scope.id = text_insert_string("Global");
		temp.name = name;
		temp.hash_size = symbol_hashsize;
		temp.hash_prime = symbol_hashprime;
		temp.compareHashPrint = compareHashPrint;
		temp.table = new Symbol[symbol_hashsize];
		temp.current = temp.scopes = scope;

		// now hook into list of tables
		temp.next = symbol_tables;
		symbol_tables = temp;

		return temp;
	}

	/** next symbol in hash list */
	Symbol next_hash;
	/** pointer to next pointer of last_symbol in hash list */
	Pointer<Symbol> last_hash = new Pointer<>();

	/** next symbol in scope list */
	Symbol next_scope;

	/** pointer to the scope symbol */
	Symbol scope;

	/** hash value for quick searching */
	int hash;

	int id;

	public void print()
	{
		text_printf(id == 0 ? "Null symbol" : text_get_string(id));
	}
}
