package org.borium.rdp;

import static org.borium.rdp.Text.TextMessageType.*;

import java.io.*;

import org.borium.rdp.Scan.*;

public class Text
{
	public enum TextMessageType
	{
		TEXT_INFO, TEXT_WARNING, TEXT_ERROR, TEXT_FATAL, TEXT_INFO_ECHO, TEXT_WARNING_ECHO, TEXT_ERROR_ECHO,
		TEXT_FATAL_ECHO
	}

	/** Maximum number of error markers per line */
	private static final int MAX_ECHO = 9;

	private static final int EXIT_FAILURE = 1;

	/** total number of errors this run */
	@SuppressWarnings("unused")
	private static int totalerrors = 0;

	/** total number of warnings this run */
	@SuppressWarnings("unused")
	private static int totalwarnings = 0;

	/** total errors for this file */
	private static int errors = 0;

	/** crash if error count exceeds this value */
	private static int maxerrors = 25;

	/** total warnings for this file */
	private static int warnings = 0;

	/** crash if warning count exceeds this value */
	private static int maxwarnings = 100;

	/** filename */
	private static String name = null;

	/** current line in this file */
	private static int linenumber = 0;

	/** TEXT_MESSAGES; */
	private static PrintStream messages = System.out;

	/** array of error positions */
	private static int[] echo_pos = new int[MAX_ECHO];

	/** current error number this line */
	private static int echo_num = -1;

	/** current text character */
	static int text_char = ' ';

	/** first character of current source line */
	private static int first_char;

	/** last character of current source line */
	@SuppressWarnings("unused")
	private static int last_char;

	/** pointer to current source character */
	static int text_current;

	/** text array for storing id's and strings */
	static char[] text_bot = null;

	/** top of text character */
	static int text_top = 1;

	/** size of text buffer */
	private static int maxtext;

	/** tab expansion width */
	@SuppressWarnings("unused")
	private static int tabwidth;

	static ScanData text_scan_data; // pointer to the last thing read by the scanner

	public static String text_default_filetype(String fname, String ftype)
	{
		if (ftype.length() == 0)
		{
			return fname;
		}
		String fullname = fname;
		if (fullname.indexOf('.') == -1)
		{
			fullname += "." + ftype;
		}
		return fullname;
	}

	/** add a new filetype. If ftype is NULL, return just filename */
	public static String text_force_filetype(String fname, String ftype)
	{
		// work backwards from end of filename looking for a dot, or a directory separator
		int length = fname.length() - 1;
		while (fname.charAt(length) != '.' && fname.charAt(length) != '/' && fname.charAt(length) != '\\' && length > 0)
		{
			length--;
		}
		if (fname.charAt(length) != '.')
		{
			length = fname.length();
		}
		String fullname = null;
		if (ftype == null)
		{
			fullname = fname;
		}
		else
		{
			fullname = fname.substring(0, length) + "." + ftype;
		}
		return fullname;
	}

	public static String text_get_string(int start)
	{
		String s = "";
		while (text_bot[start] != 0)
		{
			s += text_bot[start++];
		}
		return s;
	}

	public static void text_init(int max_text, int max_errors, int max_warnings, int tab_width)
	{
		tabwidth = tab_width;
		maxtext = max_text;
		maxerrors = max_errors;
		maxwarnings = max_warnings;

		text_bot = new char[maxtext];
		text_top = 1;
		text_current = last_char = first_char = maxtext;
	}

	public static int text_insert_char(char c)
	{
		int start = text_top;
		if (text_top >= last_char)
		{
			text_message(TEXT_FATAL, "Ran out of text space\n");
		}
		else
		{
			text_bot[text_top++] = c;
		}
		return start;
	}

	public static int text_insert_string(String str)
	{
		int start = text_top;
		for (char ch : str.toCharArray())
		{
			text_insert_char(ch);
		}
		text_insert_char((char) 0);
		return start;
	}

	public static int text_message(TextMessageType type, String message)
	{
		if (message == null)
		{
			return 0;
		}
		if (type == TEXT_INFO_ECHO || type == TEXT_WARNING_ECHO || type == TEXT_ERROR_ECHO || type == TEXT_FATAL_ECHO)
		{
			if (++echo_num < MAX_ECHO)
			{
				echo_pos[echo_num] = first_char - text_current;
			}
		}
		text_echo_line_number();
		switch (type)
		{
		case TEXT_INFO:
		case TEXT_INFO_ECHO:
			break;
		case TEXT_WARNING:
		case TEXT_WARNING_ECHO:
			warnings++;
			totalwarnings++;
			messages.print("Warning ");
			break;
		case TEXT_ERROR:
		case TEXT_ERROR_ECHO:
			errors++;
			totalerrors++;
			messages.print("Error ");
			break;
		case TEXT_FATAL:
		case TEXT_FATAL_ECHO:
			messages.print("Fatal ");
			break;
		default:
			messages.print("Unknown ");
		}
		if (type == TEXT_WARNING_ECHO || type == TEXT_ERROR_ECHO)
		{
			messages.print(echo_num + 1);
		}
		if (name != null && linenumber != 0)
		{
			messages.print("(" + name + ") ");
		}
		else if (type != TEXT_INFO && type != TEXT_INFO_ECHO)
		{
			messages.print("- ");
		}
		messages.print(message);
		if (type == TEXT_FATAL || type == TEXT_FATAL_ECHO)
		{
			System.exit(EXIT_FAILURE);
		}
		if (errors > maxerrors && maxerrors > 0)
		{
			messages.println("Fatal (" + (name == null ? "null file" : name) + "): too many errors");
			System.exit(EXIT_FAILURE);
		}
		if (warnings > maxwarnings && maxwarnings > 0)
		{
			messages.println("Fatal (" + (name == null ? "null file" : name) + "): too many warnings");
			System.exit(EXIT_FAILURE);
		}
		return message.length() + 1;
	}

	public static int text_printf(String str)
	{
		if (str != null)
		{
			for (char ch : str.toCharArray())
			{
				if (ch == '\n')
				{
					messages.print('\r');
				}
				messages.print("" + ch);
			}
		}
		return str == null ? 0 : str.length();
	}

	private static void text_echo_line_number()
	{
		if (linenumber != 0)
		{
			String s = String.format("%6d: ", linenumber);
			messages.print(s);
		}
		else
		{
			messages.print("******: ");
		}
	}
}
