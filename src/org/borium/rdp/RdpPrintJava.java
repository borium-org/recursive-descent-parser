package org.borium.rdp;

import static org.borium.rdp.CRT.*;
import static org.borium.rdp.RDP.*;
import static org.borium.rdp.RdpAux.*;
import static org.borium.rdp.Text.*;

import java.io.*;

import org.borium.rdp.RdpAux.*;

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

		iprintln("public class Compiler");
		iprintln("{");
		rdp_indentation++;

		// From now on first approach is to declare all entities that are
		// equivalent to C output counterparts
		printFileNames();
		printIntVariables();
		printTokenNames();
		printLoadKeywords();
		printDeclareAllSets(base);
		printInitializeAllSets(base);
		printParserMethods(base, true);
		printParserMethods(base, false);
		printMainRoutine();

		rdp_indentation--;
		iprintln("}");

		text_redirect(System.out);
		file.close();
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
					iprintln("private static final Set " + text_get_string(temp.id) + "_first = new Set();");
				}

				if (temp.kind == K_PRIMARY)
				{
					iprintln("private static final Set " + text_get_string(temp.id) + "_stop = new Set();");
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
					iprint(text_get_string(temp.id) + "_first.assign(");
					temp.first.print(rdp_enum_string, 78);
					println(");");
				}

				if (temp.kind == K_PRIMARY)
				{
					iprint(text_get_string(temp.id) + "_stop.assign(");
					temp.follow.print(rdp_enum_string, 78);
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

		iprintln("public enum Keyword");
		iprintln("{");
		rdp_indentation++;
		iprintln("SCAN_P_EOF(\"<EOF>\"),");
		iprintln("SCAN_P_ID(\"<Ident>\"),");
		iprintln("SCAN_P_ERROR(\"<Error>\"),");
		iprintln("SCAN_P_INTEGER(\"<Integer>\"),");
		iprintln("SCAN_P_REAL(\"<Float>\"),");
		RdpData temp = (RdpData) tokens.getScope().nextSymbolInScope();
		while (temp != null)
		{
			if (temp.kind == K_TOKEN || temp.kind == K_EXTENDED)
			{
				iprint("");
				rdp_print_parser_production_name_no_comment(temp);
				String id = "";
				String tempId = text_get_string(temp.id);
				for (char ch : tempId.toCharArray())
				{
					if (ch == '"')
						id += "\\\"";
					else
						id += ch;
				}
				if (id.equals("\\\""))
					id = "<String>";
				if (id.equals("\'"))
					id = "<Char>";
				text_printf("(\"" + id + "\"),");
				iprintln();
			}
			temp = (RdpData) temp.nextSymbolInScope();
		}
		iprintln("kw__last(null);");
		iprintln();
		iprintln("private String text;");
		iprintln();
		iprintln("private Keyword(String string)");
		iprintln("{");
		iprintln("\ttext = string;");
		iprintln("}");
		iprintln();
		iprintln("@Override");
		iprintln("public String toString()");
		iprintln("{");
		iprintln("\treturn text;");
		iprintln("}");
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

				// if (temp.close != null)
				// {
				// print("\"");
				// rdp_print_parser_string(temp.close);
				// print("\", ");
				// }
				// else
				// {
				// print("NULL, ");
				// }
				// print(text_get_string(temp.token_enum) + ", ");
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

				// rdp_print_parser_alternate(temp, temp);

				// add error handling on exit
				iprintln("scan_test_set("
						+ (rdp_error_production_name.value() ? "\"" + text_get_string(temp.id) + "\"" : "NULL") + ", "
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

	private void printTokenNames()
	{
		// TODO Auto-generated method stub

	}

	private void rdp_print_parser_param_list(String first, RdpParamList params, int definition, int start_rule)
	{
		print("(");

		/* processing for tree parameter */
		if (rdp_dir_tree != 0)
			throw new RuntimeException("rdp_dir_tree not 0");

		if (params == null && definition != 0 && rdp_dir_tree == 0)
		{
		}
		else
		{
			throw new RuntimeException("param list not null");
		}

		print(")");
	}
}
