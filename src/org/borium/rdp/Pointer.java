package org.borium.rdp;

/**
 * The helper class to emulate C-style passing by reference (pointer).
 *
 * @param <T>
 *            Any type, primitives (int, float, etc) must use wrapper type.
 */
public class Pointer<T>
{
	private T value;

	public Pointer()
	{
		value = null;
	}

	Pointer(T value)
	{
		this.value = value;
	}

	void set(T value)
	{
		this.value = value;
	}

	T value()
	{
		return value;
	}
}
