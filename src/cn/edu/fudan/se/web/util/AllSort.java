package cn.edu.fudan.se.web.util;

/**
 * 
 * 
 * @author Luminosite
 *
 */
public class AllSort
{
	private Object[] allObejcts;
	private Object[][] result;
	private int index;

	public static void main(String[] args)
	{
		String[] objects = new String[]
		{ "one", "two", "three", "four" };
		AllSort allSort = new AllSort(objects);
		Object[][] permutation = allSort.getPermutation();
		for (int i = 0; i < permutation.length; i++)
		{
			for (int j = 0; j < permutation[i].length; j++)
			{
				System.out.print(permutation[i][j] + " ");
			}
			System.out.println();
		}
	}

	public AllSort(Object[] allObjects)
	{
		this.allObejcts = allObjects;
		result = new Object[getFactorial(allObjects.length)][];
		index = 0;
	}

	public Object[][] getPermutation()
	{
		permutation(allObejcts, 0, allObejcts.length - 1);
		return result;
	}

	private int getFactorial(int number)
	{
		if (number > 1)
			return number * getFactorial(number - 1);
		else
		{
			return number;
		}

	}

	void swap(Object[] array, int n, int m)
	{
		if (n == m)
			return;
		Object tmp = array[n];
		array[n] = array[m];
		array[m] = tmp;
	}

	private void permutation(Object[] array, int start, int end)
	{
		if (start == end)
		{
			result[index++] = array.clone();

		}
		else
		{
			for (int i = start; i <= end; i++)
			{
				swap(array, start, i);
				permutation(array, start + 1, end);
				swap(array, start, i);
			}
		}
	}

}
