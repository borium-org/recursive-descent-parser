package org.borium.rdp;

import static org.borium.rdp.Text.*;

public class Symbol
{
	private static SymbolTable symbol_tables = null;

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
