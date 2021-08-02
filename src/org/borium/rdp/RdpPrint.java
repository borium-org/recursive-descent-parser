package org.borium.rdp;

import static org.borium.rdp.CRT.*;
import static org.borium.rdp.RdpAux.*;
import static org.borium.rdp.Scan.*;
import static org.borium.rdp.Text.*;

import java.util.*;

import org.borium.rdp.RdpAux.*;

public class RdpPrint
{
	static String[] rdp_enum_string;
	static String[] rdp_token_string;

	static void rdp_make_token_string(SymbolScopeData base)
	{
		RdpData p = (RdpData) base.nextSymbolInScope();

		ArrayList<String> tokens = new ArrayList<>();
		tokens.add(text_get_string(text_insert_string("IGNORE")));
		tokens.add(text_get_string(text_insert_string("ID")));
		tokens.add(text_get_string(text_insert_string("INTEGER")));
		tokens.add(text_get_string(text_insert_string("REAL")));
		tokens.add(text_get_string(text_insert_string("CHAR")));
		tokens.add(text_get_string(text_insert_string("CHAR_ESC")));
		tokens.add(text_get_string(text_insert_string("STRING")));
		tokens.add(text_get_string(text_insert_string("STRING_ESC")));
		tokens.add(text_get_string(text_insert_string("COMMENT")));
		tokens.add(text_get_string(text_insert_string("COMMENT_VISIBLE")));
		tokens.add(text_get_string(text_insert_string("COMMENT_NEST")));
		tokens.add(text_get_string(text_insert_string("COMMENT_NEST_VISIBLE")));
		tokens.add(text_get_string(text_insert_string("COMMENT_LINE")));
		tokens.add(text_get_string(text_insert_string("COMMENT_LINE_VISIBLE")));
		tokens.add(text_get_string(text_insert_string("EOF")));
		tokens.add(text_get_string(text_insert_string("EOLN")));

		while (p != null)
		{
			if (p.kind == K_TOKEN || p.kind == K_EXTENDED)
			{
				p.token_string = text_insert_char('\''); /* insert open quote */
				String str = text_get_string(p.id);
				for (char c : str.toCharArray())
				{
					if (c == '\"' || c == '\\' || c == '\'')
					{
						text_insert_char('\\');
					}
					text_insert_char(c);
				}
				text_insert_string("\'"); /* insert close quote */
				tokens.add(text_get_string(p.token_string));
			}
			p = (RdpData) p.nextSymbolInScope();
		}

		p = (RdpData) base.nextSymbolInScope();

		ArrayList<String> enums = new ArrayList<>();
		int p_ignore = text_insert_string("SCAN_P_IGNORE");
		enums.add(text_get_string(p_ignore));
		enums.add(text_get_string(text_insert_string("SCAN_P_ID")));
		enums.add(text_get_string(text_insert_string("SCAN_P_INTEGER")));
		enums.add(text_get_string(text_insert_string("SCAN_P_REAL")));
		int p_char = text_insert_string("SCAN_P_CHAR");
		enums.add(text_get_string(p_char));
		int p_char_esc = text_insert_string("SCAN_P_CHAR_ESC");
		enums.add(text_get_string(p_char_esc));
		int p_string = text_insert_string("SCAN_P_STRING");
		enums.add(text_get_string(p_string));
		int p_string_esc = text_insert_string("SCAN_P_STRING_ESC");
		enums.add(text_get_string(p_string_esc));
		int p_comment = text_insert_string("SCAN_P_COMMENT");
		enums.add(text_get_string(p_comment));
		int p_comment_visible = text_insert_string("SCAN_P_COMMENT_VISIBLE");
		enums.add(text_get_string(p_comment_visible));
		int p_comment_nest = text_insert_string("SCAN_P_COMMENT_NEST");
		enums.add(text_get_string(p_comment_nest));
		int p_comment_nest_visible = text_insert_string("SCAN_P_COMMENT_NEST_VISIBLE");
		enums.add(text_get_string(p_comment_nest_visible));
		int p_comment_line = text_insert_string("SCAN_P_COMMENT_LINE");
		enums.add(text_get_string(p_comment_line));
		int p_comment_line_visible = text_insert_string("SCAN_P_COMMENT_LINE_VISIBLE");
		enums.add(text_get_string(p_comment_line_visible));
		enums.add(text_get_string(text_insert_string("SCAN_P_EOF")));
		enums.add(text_get_string(text_insert_string("SCAN_P_EOLN")));

		while (p != null)
		{
			if (p.kind == K_TOKEN || p.kind == K_EXTENDED)
			{
				p.token_enum = text_insert_characters("RDP_T_");

				if (text_is_valid_C_id(text_get_string(p.id)))
				{
					text_insert_string(text_get_string(p.id));
				}
				else
				{
					String str = text_get_string(p.id);
					for (char c : str.toCharArray())
					{
						text_insert_integer(c);
					}
					text_insert_characters(" /* ");
					if (strcmp(str, "/*") == 0) // special case: put a /* in the comment
					{
						text_insert_char('/');
						text_insert_char(' ');
						text_insert_char('*');
					}
					else if (strcmp(str, "*/") == 0) // special case: put a */ in the comment
					{
						text_insert_char('*');
						text_insert_char(' ');
						text_insert_char('/');
					}
					else
					{
						for (char c : str.toCharArray())
						{
							text_insert_char(c);
						}
					}

					text_insert_string(" */");
				}
				enums.add(text_get_string(p.token_enum));
				if (p.kind == K_EXTENDED)
				{
					switch (p.extended_value)
					{
					case SCAN_P_CHAR:
						p.extended_enum = text_get_string(p_char);
						break;
					case SCAN_P_CHAR_ESC:
						p.extended_enum = text_get_string(p_char_esc);
						break;
					case SCAN_P_STRING:
						p.extended_enum = text_get_string(p_string);
						break;
					case SCAN_P_STRING_ESC:
						p.extended_enum = text_get_string(p_string_esc);
						break;
					case SCAN_P_COMMENT:
						p.extended_enum = text_get_string(p_comment);
						break;
					case SCAN_P_COMMENT_VISIBLE:
						p.extended_enum = text_get_string(p_comment_visible);
						break;
					case SCAN_P_COMMENT_NEST:
						p.extended_enum = text_get_string(p_comment_nest);
						break;
					case SCAN_P_COMMENT_NEST_VISIBLE:
						p.extended_enum = text_get_string(p_comment_nest_visible);
						break;
					case SCAN_P_COMMENT_LINE:
						p.extended_enum = text_get_string(p_comment_line);
						break;
					case SCAN_P_COMMENT_LINE_VISIBLE:
						p.extended_enum = text_get_string(p_comment_line_visible);
						break;
					}
				}
				else
				{
					p.extended_enum = text_get_string(p_ignore);
				}
			}
			p = (RdpData) p.nextSymbolInScope();
		}
		rdp_token_string = tokens.toArray(new String[0]);
		rdp_enum_string = enums.toArray(new String[0]);
	}
}
