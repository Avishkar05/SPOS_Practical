import java.io.*;
class pasone2
{
	public static void main(String ar[])throws IOException
	{

		int i;
		String a[][]={                          //string array
			{"","START","101",""},
			{"","MOVER","BREG","ONE"},
			{"","MOVEM","BREG","TERM"},
			{"AGAIN","MULT","BREG","TERM"},
			{"","MOVER","CREG","TERM"},
			{"","ADD","CREG","N"},
			{"","MOVEM","CREG","RESULT"},
			{"N","DS","6",""},
			{"RESULT","DS","6",""},
			{"ONE","DC","1",""},
			{"TERM","DS","3",""},
			{"END","","",""}
			};
		int lc=Integer.parseInt(a[0][2]);//parsing string arguements into an integer object(returns-101)
		String st[][]=new String[5][2];//create object & variable(st)->refer to object in the heap memory.
		int cnt=0,l;
		for(i=0;i<11;i++)
		{
			if(a[i][0]!="")
			{
				st[cnt][0]=a[i][0];
			   st[cnt][1]=Integer.toString(lc);//used to return string object represented by this integer value.
				cnt++;
				if(a[i][1]=="DS")
				{
					l=Integer.parseInt(a[i][2]);//returns 6,6,3
					lc=lc+l;
				}
				else
				{
					lc++;
				}
			}
			else
			{
				lc++;
			}
		}
		System.out.println("SYMBOL\t\tVALUE\n");
		for(i=0;i<5;i++)
		{
			System.out.println(st[i][0]+"\t\t"+st[i][1]);
		}
	}
}
