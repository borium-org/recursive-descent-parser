package org.borium.rdp;

import java.util.*;

public class SymbolScopeData extends Symbol
{
	/**
	 * Sort a scope region. Don't change positions in the hash table: just move pointers in the scope chain
	 */
	void sort()
	{
		Symbol s = this;
		// attempt to sort empty list
		if (s.next_scope == null)
		{
			return;
		}
		// attempt to sort list of one
		if (s.next_scope.next_scope == null)
		{
			return;
		}
		ArrayList<Symbol> list = new ArrayList<Symbol>();
		Symbol temp_scope = s.next_scope;
		while (temp_scope != null)
		{
			// FIXME sometimes instead of next_scope being null it points to an
			// object already in the list
			if (list.contains(temp_scope))
			{
				break;
			}
			list.add(temp_scope);
			// System.out.println("Added " + text_get_string(temp_scope.id) +
			// " "
			// + temp_scope);
			temp_scope = temp_scope.next_scope;
		}
		Symbol[] array = list.toArray(new Symbol[0]);
		Arrays.sort(array);
		for (Symbol sym : array)
		{
			sym.next_scope = null;
			s.next_scope = sym;
			s = sym;
		}
	}
}
