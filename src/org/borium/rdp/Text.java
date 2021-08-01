package org.borium.rdp;

import static org.borium.rdp.Text.TextMessageType.*;

import java.io.*;

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

	/** first character of current source line */
	private static int first_char;

	/** pointer to current source character */
	static int text_current;

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
