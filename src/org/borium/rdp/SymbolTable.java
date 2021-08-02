package org.borium.rdp;

import static org.borium.rdp.Text.*;

public class SymbolTable
{
	/** an identifying string */
	String name;

	/** table of pointers to hash lists */
	Symbol[] table;

	/** pointers to chain of scope lists */
	SymbolScopeData current;

	/** pointer to first scope list */
	SymbolScopeData scopes;

	/** number of buckets in symbol table */
	int hash_size;
	/** hashing prime: hashsize and hashprime should be coprime */
	int hash_prime;
	CompareHashPrint compareHashPrint;

	/** pointer to last declared symbol table */
	SymbolTable next;

	int compare(String key, Symbol p)
	{
		return compareHashPrint.compare(key, p);
	}

	/** return current scope */
	SymbolScopeData getScope()
	{
		return current;
	}

	int hash(int prime, String key)
	{
		return compareHashPrint.hash(prime, key);
	}

	/** insert a symbol at head of hash list */
	Symbol insert(Symbol symbol)
	{
		Symbol s = symbol;
		s.hash = hash(hash_prime, text_get_string(symbol.id));
		int hash_index = s.hash % hash_size;
		s.next_hash = table[hash_index];
		table[hash_index] = s;
		s.last_hash.set(table[hash_index]);
		/* if this wasn't the start of a new list ... */
		if (s.next_hash != null)
		{
			/* ...point old list next back at s */
			s.next_hash.last_hash.set(s.next_hash);
		}
		/* now insert in scope list */
		s.next_scope = current.next_scope;
		current.next_scope = s;
		/* set up pointer to scope block */
		s.scope = current;
		return symbol;
	}
}
