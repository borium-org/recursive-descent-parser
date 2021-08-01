package org.borium.rdp;

import static org.borium.rdp.Arg.ArgKind.*;

public class Arg
{
	protected enum ArgKind
	{
		ARG_BLANK, ARG_BOOLEAN, ARG_NUMERIC, ARG_STRING
	}

	private static class arg_data
	{
		ArgKind kind;
		char key;
		String description;
		/** Booleans were in RDP 1.5 stored in an integer field, so there's the source for this mismatch. */
		@SuppressWarnings("unused")
		Pointer<Boolean> intvalue;
		/** All truly integer values are unsigned, no negative integers were used in making this app. */
		@SuppressWarnings("unused")
		Pointer<Integer> unsignedvalue;
		@SuppressWarnings("unused")
		Pointer<String> str;
		arg_data next;
	}

	private static final int EXIT_FAILURE = 1;

	private static arg_data base = null;

	public static void arg_boolean(char key, String description, Pointer<Boolean> intvalue)
	{
		add_node(ARG_BOOLEAN, key, description, intvalue, null, null);
	}

	public static void arg_help(String msg)
	{
		System.out.print("\n\nFatal - " + (msg == null ? "" : msg) + "\n\n");
		print(base);
		System.exit(EXIT_FAILURE);
	}

	public static void arg_message(String description)
	{
		add_node(ARG_BLANK, '\0', description, null, null, null);
	}

	public static void arg_numeric(char key, String description, Pointer<Integer> unsignedvalue)
	{
		add_node(ARG_NUMERIC, key, description, null, unsignedvalue, null);
	}

	public static void arg_string(char key, String description, Pointer<String> str)
	{
		add_node(ARG_STRING, key, description, null, null, str);
	}

	private static void add_node(ArgKind kind, char key, String description, Pointer<Boolean> intvalue,
			Pointer<Integer> unsignedvalue, Pointer<String> str)
	{
		arg_data temp = new arg_data();
		temp.kind = kind;
		temp.key = key;
		temp.description = description;
		temp.intvalue = intvalue;
		temp.unsignedvalue = unsignedvalue;
		temp.str = str;
		temp.next = base;
		base = temp;
	}

	private static void print(arg_data p)
	{
		if (p != null)
		{
			print(p.next);
			if (p.kind != ARG_BLANK)
			{
				System.out.print(
						"-" + p.key + (p.kind == ARG_NUMERIC ? "<n>" : p.kind == ARG_STRING ? "<s>" : "   ") + " ");
			}
			System.out.println(p.description);
		}
	}
}
