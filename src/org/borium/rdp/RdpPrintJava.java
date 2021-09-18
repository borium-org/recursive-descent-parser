package org.borium.rdp;

import static org.borium.rdp.CRT.*;
import static org.borium.rdp.RDP.*;
import static org.borium.rdp.RdpAux.*;
import static org.borium.rdp.Set.*;
import static org.borium.rdp.Text.*;
import static org.borium.rdp.Text.TextMessageType.*;

import java.io.*;

import org.borium.rdp.RdpAux.*;

@SuppressWarnings("unused")
public class RdpPrintJava extends RdpPrint
{
	/** Path where the files will be written */
	private String outputPath;
	/** Package for all generated classes */
	private String classPackage;

	/**
	 * @param outputPath
	 *                     Path where the files will be written.
	 * @param classPackage
	 *                     Package for all generated classes.
	 */
	public RdpPrintJava(String outputPath, String classPackage)
	{
		this.outputPath = outputPath;
		this.classPackage = classPackage;
	}

	/**
	 * @param rdp_base
	 *                    The root of RDP data structures for the parser
	 * @param parser_only
	 *                    True to generate only the parser, false to generate AST classes
	 */
	public void print(SymbolScopeData rdp_base, boolean parser_only)
	{
		printKeywordJava();
		printCompilerJava(rdp_base);
	}

	@Override
	protected int indent()
	{
		for (int temp = 0; temp < rdp_indentation; temp++)
		{
			text_printf("\t");
		}
		return rdp_indentation * 4;
	}

	@Override
	protected int indent(int extraIndent)
	{
		for (int temp = 0; temp < rdp_indentation + extraIndent; temp++)
		{
			text_printf("\t");
		}
		return rdp_indentation * 4;
	}

	private PrintStream createFile(String className)
	{
		PrintStream file = null;
		try
		{
			String classPath = classPackage.replace('.', '/');
			File f = new File(outputPath + "/" + classPath);
			f.mkdirs();
			file = new PrintStream(outputPath + "/" + classPath + "/" + className + ".java");
			rdp_indentation = 0;
		}
		catch (FileNotFoundException e)
		{
			throw new RuntimeException();
		}
		return file;
	}

	private void printCompilerJava(SymbolScopeData base)
	{
		PrintStream file = createFile("Compiler");
		text_redirect(file);

		iprintln("package " + classPackage + ";");
		iprintln();
		iprintln("import static " + classPackage + ".Keyword.*;");
		iprintln("import static " + classPackage + ".Scanner.*;");
		iprintln("import static " + classPackage + ".Text.*;");
		iprintln("import static " + classPackage + ".Text.TextMessageType.*;");

		iprintln();
		iprintln("import org.borium.jrc.parser.ast.*;");
		iprintln();

		iprintln("public class Compiler");
		iprintln("{");
		rdp_indentation++;

		printDeclareAllSets(base);
		printStaticInit();
		printInitializeAllSets(base);
		printConstructor();

		// From now on first approach is to declare all entities that are
		// equivalent to C output counterparts
		printFileNames();
		printIntVariables();
		printTokenNames();
		printParserMethods(base, true);
		printParserMethods(base, false);
		printMainRoutine();

		printLoadKeywords();

		rdp_indentation--;
		iprintln("}");

		text_redirect(System.out);
		file.close();
	}

	private void printConstructor()
	{
		iprintln("public Compiler()");
		iprintln("{");
		rdp_indentation++;
		iprintln("loadKeywords();");
		rdp_indentation--;
		iprintln("}");
		iprintln();
	}

	private void printDeclareAllSets(SymbolScopeData base)
	{
		RdpData temp = (RdpData) base.nextSymbolInScope();
		while (temp != null)
		{
			if (rdp_production_set.includes(temp.kind) && temp.code_only == 0)
			{
				if (temp.first_cardinality > 1)
				{
					iprintln("private static final TokenSet " + text_get_string(temp.id) + "_first = new TokenSet();");
				}

				if (temp.kind == K_PRIMARY)
				{
					iprintln("private static final TokenSet " + text_get_string(temp.id) + "_stop = new TokenSet();");
				}
			}
			temp = (RdpData) temp.nextSymbolInScope();
		}
		iprintln();
	}

	private void printFileNames()
	{
		// TODO Auto-generated method stub

	}

	private void printInitializeAllSets(SymbolScopeData base)
	{
		iprintln("private static void setInitialize()");
		iprintln("{");
		rdp_indentation++;

		RdpData temp = (RdpData) base.nextSymbolInScope();
		while (temp != null)
		{
			if (rdp_production_set.includes(temp.kind) && temp.code_only == 0)
			{
				if (temp.first_cardinality > 1)
				{
					int initial = iprint(text_get_string(temp.id) + "_first.assignList(");
					temp.first.print(rdp_enum_string, initial, this::indent, 120, false);
					println(");");
				}

				if (temp.kind == K_PRIMARY)
				{
					int initial = iprint(text_get_string(temp.id) + "_stop.assignList(");
					temp.follow.print(rdp_enum_string, initial, this::indent, 120, false);
					println(");");
				}
			}
			temp = (RdpData) temp.nextSymbolInScope();
		}

		rdp_indentation--;
		iprintln("}");
		iprintln();
	}

	private void printIntVariables()
	{
		// TODO Auto-generated method stub

	}

	private void printKeywordJava()
	{
		PrintStream file = createFile("Keyword");
		text_redirect(file);

		iprintln("package " + classPackage + ";");
		iprintln();

		iprintln("import static " + classPackage + ".Scanner.*;");
		iprintln();

		iprintln("public interface Keyword");
		iprintln("{");
		rdp_indentation++;
		// iprintln("SCAN_P_EOF(\"<EOF>\"),");
		// iprintln("SCAN_P_ID(\"<Ident>\"),");
		// iprintln("SCAN_P_ERROR(\"<Error>\"),");
		// iprintln("SCAN_P_INTEGER(\"<Integer>\"),");
		// iprintln("SCAN_P_REAL(\"<Float>\"),");
		// RdpData temp = (RdpData) tokens.getScope().nextSymbolInScope();
		// while (temp != null)
		// {
		// if (temp.kind == K_TOKEN || temp.kind == K_EXTENDED)
		// {
		// iprint("");
		// rdp_print_parser_production_name_no_comment(temp);
		// String id = "";
		// String tempId = text_get_string(temp.id);
		// for (char ch : tempId.toCharArray())
		// {
		// if (ch == '"')
		// id += "\\\"";
		// else
		// id += ch;
		// }
		// if (id.equals("\\\""))
		// id = "<String>";
		// if (id.equals("\'"))
		// id = "<Char>";
		// text_printf("(\"" + id + "\"),");
		// iprintln();
		// }
		// temp = (RdpData) temp.nextSymbolInScope();
		// }
		// iprintln("kw__last(null);");
		// iprintln();
		// iprintln("private String text;");
		// iprintln();
		// iprintln("private Keyword(String string)");
		// iprintln("{");
		// iprintln("\ttext = string;");
		// iprintln("}");
		// iprintln();
		// iprintln("@Override");
		// iprintln("public String toString()");
		// iprintln("{");
		// iprintln("\treturn text;");
		// iprintln("}");

		////// C
		RdpData temp = (RdpData) tokens.getScope().nextSymbolInScope();
		iprintln("int RDP_TT_BOTTOM = SCAN_P_TOP;");
		int offset = 0;
		while (temp != null)
		{
			if (temp.kind == K_TOKEN || temp.kind == K_EXTENDED)
			{
				iprint("int ");
				rdp_print_parser_production_name_no_comment(temp);
				println(" = SCAN_P_TOP + " + offset + ";");
				offset++;
			}
			temp = (RdpData) temp.nextSymbolInScope();
		}
		iprintln("int RDP_TT_TOP = SCAN_P_TOP + " + offset + ";");
		////// C

		rdp_indentation--;
		iprintln("}");
		text_redirect(System.out);
		file.close();
	}

	private void printLoadKeywords()
	{
		iprintln("private void loadKeywords()");
		iprintln("{");
		rdp_indentation++;

		RdpData temp = (RdpData) tokens.getScope().nextSymbolInScope();
		while (temp != null)
		{
			if (temp.kind == K_TOKEN || temp.kind == K_EXTENDED)
			{
				iprint("scanLoadKeyword(\"");
				rdp_print_parser_string(text_get_string(temp.id));
				print("\", ");

				if (temp.close != null)
				{
					print("\"");
					rdp_print_parser_string(temp.close);
					print("\", ");
				}
				else
				{
					print("null, ");
				}
				String token = text_get_string(temp.token_enum).split(" ")[0];
				print(token + ", ");
				print(temp.extended_enum);
				println(");");
			}
			temp = (RdpData) temp.nextSymbolInScope();
		}

		rdp_indentation--;
		iprintln("}");
		iprintln();
	}

	private void printMainRoutine()
	{
		// TODO Auto-generated method stub

	}

	private void printParserMethods(SymbolScopeData base, boolean start)
	{
		// print parser definitions using rdp_print_parser_primaries(base);

		for (RdpData temp = (RdpData) base.nextSymbolInScope(); temp != null; temp = (RdpData) temp.nextSymbolInScope())
		{
			if (temp.kind == K_PRIMARY && temp.call_count > 0 && temp.code_only == 0)
			{
				if (temp == rdp_start_prod && !start)
					continue;
				if (temp != rdp_start_prod && start)
					continue;
				boolean is_void = temp.return_type.equals("void") && temp.return_type_stars == 0;
				if (!is_void)
					throw new RuntimeException("Non-void " + text_get_string(temp.id));

				iprint(temp == rdp_start_prod ? "public " : "protected ");
				boolean parserOnly = rdp_parser_only.value();
				String astType = "Ast" + capitalizeFirst(text_get_string(temp.id));
				print(parserOnly ? "void" : astType);
				text_printf(" " + text_get_string(temp.id));

				rdp_print_parser_param_list(null, temp.params, 1, 0);
				println();
				iprintln("{");
				rdp_indentation++;

				if (temp.ll1_violation != 0)
				{
					iprintln("// WARNING - an LL(1) violation was detected at this point in the grammar");
				}

				if (!parserOnly)
				{
					iprintln(astType + " ast = new " + astType + "();");
					iprintln();
				}

				// In trace mode, add an entry message
				if (rdp_trace.value())
				{
					iprintln("text_message(TEXT_INFO, \"Entered \'" + text_get_string(temp.id) + "\'\\n\");");
					iprintln();
				}

				rdp_print_parser_alternate(temp, temp);

				// add error handling on exit
				iprintln("scan_test_set("
						+ (rdp_error_production_name.value() ? "\"" + text_get_string(temp.id) + "\"" : "null") + ", "
						+ text_get_string(temp.id) + "_stop, " + text_get_string(temp.id) + "_stop);");

				iprintln();

				// In trace mode, add an exit message
				if (rdp_trace.value())
				{
					iprintln("text_message(TEXT_INFO, \"Exited \'" + text_get_string(temp.id) + "\'\\n\");");
					iprintln();
				}

				if (!parserOnly)
				{
					iprintln("return ast;");
				}

				rdp_indentation--;
				iprintln("}");
				println();
			}
		}
	}

	private void printStaticInit()
	{
		iprintln("static");
		iprintln("{");
		rdp_indentation++;
		iprintln("setInitialize();");
		rdp_indentation--;
		iprintln("}");
		iprintln();
	}

	private void printTokenNames()
	{
		// TODO Auto-generated method stub

	}

	private void rdp_print_parser_alternate(RdpData production, RdpData primary)
	{
		RdpList list = production.list;
		if (list.next == null)
		{
			rdp_print_parser_sequence(list.production, primary);
		}
		else
		{
			while (list != null)
			{
				if (list.production.kind != K_SEQUENCE)
				{
					text_message(TEXT_FATAL, "internal error - expecting alternate\n");
				}

				indent();

				text_printf("if (");
				rdp_print_parser_test(list.production.id, list.production.first, null);
				text_printf(")\n");
				indent();
				text_printf("{\n");
				rdp_indentation++;

				rdp_print_parser_sequence(list.production, primary);

				rdp_indentation--;
				indent();

				text_printf("}\n");

				if ((list = list.next) != null)
				{
					indent();
					text_printf("else\n");
				}
				else
				/* tail test at end of alternates */
				if (!(production.contains_null && production.lo != 0))
				{
					indent();
					text_printf("else\n");
					rdp_indentation++;
					indent();
					rdp_print_parser_test(production.id, production.first, text_get_string(primary.id));
					rdp_indentation--;
					indent();
					text_printf(";\n");
				}
			}
		}
	}

	private void rdp_print_parser_item(RdpData prod, RdpData primary, String return_name, RdpParamList actuals,
			int promote_epsilon, int promote, String default_action)
	{
		if (promote == PROMOTE_DEFAULT)
		{
			promote = prod.promote_default;
		}

		if (!(prod.kind == K_CODE && prod.code_successor != 0))
		{
			indent(); /* Don't indent code sequence-internal or inline items */
		}

		switch (prod.kind)
		{
		case K_INTEGER:
		case K_REAL:
		case K_STRING:
		case K_EXTENDED:
		case K_TOKEN:
			if (rdp_dir_tree != 0)
			{
				if (promote == PROMOTE_DONT)
				{
					/* add a tree node for this scanner item as child of current parent */
					text_printf("if (rdp_tree_update) rdp_add_child(null, rdp_tree);\n");
					indent();
				}
				else if (promote == PROMOTE_AND_COPY)
				{
					/* copy scanner data to current tree parent */
					text_printf("if (rdp_tree_update) memcpy(rdp_tree, text_scan_data, sizeof(scan_data));\n");
					indent();
				}
				else if (promote == PROMOTE_ABOVE)
				{
					/* add a tree node for this scanner item as parent of current parent */
					text_printf("if (rdp_tree_update) rdp_add_parent(null, rdp_tree);\n");
					indent();
				}
			}
			text_printf("scan_test("
					+ (rdp_error_production_name.value() ? "\"" + text_get_string(primary.id) + "\"" : "null") + ", ");
			rdp_print_parser_production_name(prod);
			text_printf(", " + text_get_string(primary.id) + "_stop);\n");
			indent();
			/* disable if -p option used */
			if (return_name != null && !rdp_parser_only.value())
			{
				text_printf(return_name + " = text_scan_data."
						+ (prod.kind == K_REAL ? "data.r" : prod.kind == K_INTEGER ? "data.i" : "id") + ";\n");
				indent();
			}
			text_printf("scan_();\n");
			break;
		case K_CODE:
			if (!rdp_parser_only.value()) /* disabled by -p option */
			{
				if (prod.code_pass != 0)
				{
					text_printf("if (rdp_pass == " + prod.code_pass + ") { \\\n");
				}
				String temp = text_get_string(prod.id);
				for (char ch : temp.toCharArray())
				{
					if (ch == '\n')
					{
						text_printf("\\\n");
					}
					else if (isprint(ch))
					{
						text_printf("" + ch);
					}
				}

				if (prod.code_pass != 0)
				{
					text_printf(" \\\n}");
				}

				if (prod.kind == K_CODE && prod.code_terminator != 0)
				{
					text_printf("\n"); /* terminate semantic actions tidily */
				}
			}
			break;
		case K_PRIMARY:
			if (rdp_dir_tree != 0 && promote == PROMOTE_AND_COPY)
			{
				text_printf("if(rdp_tree_update) {rdp_tree.id = \"" + text_get_string(prod.id)
						+ "\"; rdp_tree.token = 0;}\n");
			}
			if (return_name != null && !rdp_parser_only.value())
			{
				text_printf(return_name + " = ");
			}
			text_printf(text_get_string(prod.id));
			if (prod.code_only == 0 && actuals == null)
			{
				rdp_print_parser_param_list(promote == PROMOTE_DONT ? text_get_string(prod.id) : null, actuals, 0, 0);
			}
			text_printf(";\n");
			break;
		case K_SEQUENCE:
			text_message(TEXT_FATAL, "internal error - unexpected alternate in sequence\n");
			break;
		case K_LIST:
			rdp_print_parser_subproduction(prod, primary, promote_epsilon, default_action);
			break;
		default:
			text_message(TEXT_FATAL, "internal error - unexpected kind found\n");
		}
	}

	private void rdp_print_parser_param_list(String first, RdpParamList params, int definition, int start_rule)
	{
		// print("(");
		//
		// /* processing for tree parameter */
		// if (rdp_dir_tree != 0)
		// throw new RuntimeException("rdp_dir_tree not 0");
		//
		// if (params == null && definition != 0 && rdp_dir_tree == 0)
		// {
		// }
		// else
		// {
		// throw new RuntimeException("param list not null");
		// }
		//
		// print(")");
		text_printf("(");

		/* processing for tree parameter */
		if (rdp_dir_tree != 0)
		{
			if (definition != 0)
			{
				text_printf("rdp_tree_node_data* rdp_tree");
			}
			else
			{
				if (first == null)
				{
					text_printf("rdp_tree");
				}
				else
				{
					text_printf((start_rule != 0 ? "rdp_tree_root = " : "") + "rdp_add_"
							+ (start_rule != 0 ? "node" : "child") + "(\"" + first + "\", rdp_tree)");
				}
			}

			if (params != null)
			{
				text_printf(", "); /* put in separator for rest of parameters */
			}
		}

		if (params == null && definition != 0 && rdp_dir_tree == 0)
		{
			text_printf("");
		}
		else
		{
			rdp_print_parser_param_list_sub(params, 1, definition);
		}

		text_printf(")");
	}

	private void rdp_print_parser_param_list_sub(RdpParamList param, int last, int definition)
	{
		if (param != null)
		{
			rdp_print_parser_param_list_sub(param.next, 0, definition);
			text_printf(definition != 0 ? param.type : "");

			if (definition != 0)
			{
				for (int count = 0; count < param.stars; count++)
				{
					text_printf("*");
				}
			}

			text_printf(definition != 0 ? " " : "");
			switch (param.flavour)
			{
			case PARAM_INTEGER:
				text_printf(Integer.toString(param.n));
				break;
			case PARAM_REAL:
				text_printf(Double.toString(param.r));
				break;
			case PARAM_STRING:
				text_printf("\"" + param.id + "\"");
				break;
			case PARAM_ID:
				text_printf(param.id);
				break;
			}
			text_printf(last != 0 ? "" : ", ");
		}
	}

	private void rdp_print_parser_sequence(RdpData production, RdpData primary)
	{
		RdpList list = production.list;

		while (list != null)
		{
			rdp_print_parser_item(list.production, primary, list.return_name, list.actuals, list.promote_epsilon,
					list.promote, list.default_action);
			list = list.next;
		}
	}

	private void rdp_print_parser_subproduction(RdpData prod, RdpData primary, int promote_epsilon,
			String default_action)
	{
		if (prod.lo == 0) /* this can be an optional body */
		{
			text_printf("if (");
			rdp_print_parser_test(prod.id, prod.first, null);
			text_printf(")\n");
			indent();
		}

		println("{");
		rdp_indentation++;
		iprintln("// Start of " + text_get_string(prod.id));

		if (prod.ll1_violation != 0)
		{
			iprintln("// FIXME - an LL(1) violation was detected at this point in the grammar");
		}

		/* We don't need to instantiate count if hi is infinity and lo is 0 or 1 */
		if (!((prod.hi == 0 || prod.hi == 1) && (prod.lo == 1 || prod.lo == 0)))
		{
			indent();
			text_printf("unsigned long rdp_count = 0;\n");
		}

		iprintln("while (true)");
		iprintln("{");
		rdp_indentation++;

		/* Put in test that first element of body matches if iterator low count > 0 and prod isn't nullable */
		if (prod.lo != 0 && !prod.contains_null)
		{
			indent();
			rdp_print_parser_test(prod.id, prod.first, text_get_string(primary.id));
			println(";");
		}

		rdp_print_parser_alternate(prod, primary);

		if (!((prod.hi == 0 || prod.hi == 1) && (prod.lo == 1 || prod.lo == 0)))
		{
			iprintln("rdp_count++;");
		}

		if (prod.hi > 1) /* Don't bother testing rdp_count of hi is zero or infty */
		{
			iprintln("if (rdp_count == " + prod.hi + ")");
			iprintln(1, "break;");
		}

		if (prod.supplementary_token != null)
		{
			iprintln("if (text_scan_data.token != " + text_get_string(prod.supplementary_token.token_enum) + ")");
			iprintln(1, "break;");

			if (rdp_dir_tree != 0)
			{
				if (prod.delimiter_promote == PROMOTE_DONT)
				{
					/* add a tree node for this scanner item */
					text_printf("if (rdp_tree_update) rdp_add_child(null, rdp_tree);\n");
					indent();
				}
				else if (prod.delimiter_promote == PROMOTE_AND_COPY)
				{
					/* copy scanner data to current tree parent */
					text_printf("if (rdp_tree_update) memcpy(rdp_tree, text_scan_data, sizeof(scan_data));\n");
					indent();
				}
			}

			iprintln("scan_();"); /* skip list token */
		}
		else if (prod.hi != 1)
		{
			iprint("if (!");
			rdp_print_parser_test(prod.id, prod.first, null);
			println(")");
			iprintln(1, "break;");
		}

		if (prod.hi == 1)
		{
			iprintln("break; // hi limit is 1");
		}

		rdp_indentation--;
		iprintln("}");

		if (prod.lo > 1) /* test rdp_count on way out */
		{
			indent();
			text_printf("if (rdp_count < " + prod.lo + ")");
			text_printf("  text_message(TEXT_ERROR_ECHO, \"iteration count too low\\n\");\n");
		}

		iprintln("// End of " + text_get_string(prod.id));
		rdp_indentation--;
		iprintln("}");

		if (prod.lo == 0 && (rdp_dir_tree != 0 || default_action != null))
		{
			iprintln("else");
			indent();
			iprintln("{");
			rdp_indentation++;
			indent();
			text_printf("/* default action processing for " + text_get_string(prod.id) + "*/\n");
			if (rdp_dir_tree != 0)
			{
				/* First do tree node handling */
				if (promote_epsilon == PROMOTE_DONT)
				{
					/* add an epsilon tree node */
					indent();
					if (rdp_dir_annotated_epsilon_tree != 0)
					{
						text_printf(
								"if (rdp_tree_update) {rdp_tree_node_data *temp = rdp_add_child(null, rdp_tree); temp->id = \"#: "
										+ text_get_string(prod.id + 4) + "\"; temp.token = SCAN_P_ID;}\n");
					}
					else
					{
						text_printf(
								"if (rdp_tree_update) {rdp_tree_node_data *temp = rdp_add_child(null, rdp_tree); temp->id = null; temp->token = SCAN_P_ID;}\n");
					}
				}
				else if (promote_epsilon == PROMOTE_AND_COPY)
				{
					/* copy epsilon to current tree parent */
					indent();
					if (rdp_dir_annotated_epsilon_tree != 0)
					{
						text_printf("if (rdp_tree_update) {rdp_tree->id = \"#: " + text_get_string(prod.id + 4)
								+ "\"; rdp_tree.token = SCAN_P_ID;}\n");
					}
					else
					{
						text_printf("if (rdp_tree_update) {rdp_tree->id = null; rdp_tree->token = SCAN_P_ID;}\n");
					}
				}
			}

			/* Now copy out default action */
			/* disabled by -p option */
			if (!rdp_parser_only.value() && default_action != null)
			{
				for (char ch : default_action.toCharArray())
				{
					if (ch == '\n')
					{
						text_printf(" \\\n");
					}
					else
					{
						text_printf("" + ch);
					}
				}
				println(); /* terminate semantic actions tidily */
			}
			rdp_indentation--;
			iprintln("}");
		}
	}

	private void rdp_print_parser_test(int first_name, Set first, String follow_name)
	{
		text_printf("scan_test");

		switch (set_cardinality(first))
		{
		default:
			text_printf(
					"_set(" + (rdp_error_production_name.value() ? "\"" + text_get_string(first_name) + "\"" : "null")
							+ ", " + text_get_string(first_name) + "_first");
			break;
		case 1:
			text_printf("(" + (rdp_error_production_name.value() ? "\"" + text_get_string(first_name) + "\"" : "null")
					+ ", ");
			first.print(rdp_enum_string, 120);
			break;
		case 0:
			System.err.println("Set " + first + " is empty");
		}

		if (follow_name == null)
		{
			text_printf(", null)");
		}
		else
		{
			text_printf(", " + follow_name + "_stop)");
		}
	}
}
