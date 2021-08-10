package org.borium.rdp;

import static org.borium.rdp.Text.*;

public class Symbol implements Comparable<Symbol>
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

	/** lookup a symbol by id. Return null if it is not found */
	public static Symbol symbol_lookup_key(SymbolTable table, String key, SymbolScopeData scope)
	{
		int hash = table.hash(table.hash_prime, key);
		Symbol p = table.table[hash % table.hash_size];

		// look for symbol with same hash and a true compare
		while (!(p == null || table.compare(key, p) == 0 && !(p.scope != scope && scope != null)))
		{
			p = p.next_hash;
		}

		return p;
	}

	public static SymbolScopeData symbol_new_scope(SymbolTable table, String id)
	{
		SymbolScopeData p = new SymbolScopeData();

		p.id = text_insert_string(id);
		p.next_hash = table.scopes;
		table.current = table.scopes = p;
		if (p.next_hash != null)
			p.next_hash.last_hash.set(p.next_hash);
		return p;
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
	final Pointer<Symbol> last_hash = new Pointer<>();

	/** next symbol in scope list */
	Symbol next_scope;

	/** pointer to the scope symbol */
	Symbol scope;

	/** hash value for quick searching */
	int hash;

	int id;

	@Override
	public int compareTo(Symbol other)
	{
		return text_get_string(id).compareTo(text_get_string(other.id));
	}

	/** Return next symbol in scope chain. Return NULL if at end */
	public Symbol nextSymbolInScope()
	{
		return next_scope;
	}

	public void print()
	{
		text_printf(id == 0 ? "Null symbol" : text_get_string(id));
	}

	protected void unlinkSymbol()
	{
		Symbol s = this;

		s.last_hash.set(s.next_hash); /* point previous pointer to next symbol */
		if (s.next_hash != null)
		{
			s.next_hash.last_hash.set(s.last_hash.value());
		}
	}
}
