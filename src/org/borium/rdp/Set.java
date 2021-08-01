package org.borium.rdp;

public class Set
{
	private long[] data = new long[10];

	public void assignList(int... bits)
	{
		clear();
		for (int bit : bits)
		{
			set(bit);
		}
	}

	public void clear()
	{
		for (int i = 0; i < data.length; i++)
		{
			data[i] = 0;
		}
	}

	public void set(int element)
	{
		grow(element);
		int index = element / 32;
		element &= 0x1F;
		data[index] |= 1 << element;
	}

	private void grow(int bits)
	{
		int index = (bits + 31) / 32;
		if (index >= data.length)
		{
			long[] newData = new long[index + 5];
			for (int i = 0; i < newData.length; i++)
			{
				newData[i] = 0;
			}
			for (int i = 0; i < data.length; i++)
			{
				newData[i] = data[i];
			}
			data = newData;
		}
	}
}
