package org.borium.rdp;

public class RdpAux
{
	/** force output files flag */
	public static Pointer<Boolean> rdp_force = new Pointer<>(false);

	/** flag to force writing of production name into error messages */
	public static Pointer<Boolean> rdp_error_production_name = new Pointer<>(false);

	/** flag to generate expanded bnf listing */
	public static Pointer<Boolean> rdp_expanded = new Pointer<>(false);

	/** omit semantic actions flag */
	public static Pointer<Boolean> rdp_parser_only = new Pointer<>(false);

	/** add trace messages flag */
	public static Pointer<Boolean> rdp_trace = new Pointer<>(false);
}
