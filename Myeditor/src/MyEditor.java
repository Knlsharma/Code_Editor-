import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
class MyEditor implements ActionListener
{
	JFrame jf;
	JLabel jl;
	JTextField jtf;
	JTextArea jta,jta1;
	JButton jbcompile,jbrun;
	JScrollPane jsp,jsp1;
	Runtime r;
	String str="";
	String fname="";
	String result="";
	String result1="",result3="";
	MyEditor()
	{
		jf=new JFrame("MyEditor");
		jf.setLayout(null);
		jl=new JLabel("Enter java class name");
		jl.setBounds(20,20,130,25);
		jtf=new JTextField();
		jtf.setBounds(180,20,230,25);
		jta=new JTextArea(50,50);
		jta.addFocusListener(new MyFocusListener(this));
		jta1=new JTextArea(50,50);
		jta.setFont(new Font("varinda",Font.PLAIN,15));
		jta1.setFont(new Font("varinda",Font.PLAIN,15));
		jsp=new JScrollPane(jta);
		jsp1=new JScrollPane(jta1);
		jsp.setBounds(50,60,320,150);
		jsp1.setBounds(50,270,320,150);
		jf.add(jsp);
		jf.add(jsp1);
		jbcompile=new JButton("compile");
		jbrun=new JButton("run");
		jbcompile.setBounds(100,230,80,25);
		jbrun.setBounds(280,230,80,25);
		jf.add(jl);
		jf.add(jtf);
		r=Runtime.getRuntime();
		jf.add(jbcompile);
		jf.add(jbrun);
		jbcompile.addActionListener(this);
		jbrun.addActionListener(this);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(550,550);
		jf.setVisible(true);
	}
		public void actionPerformed(ActionEvent e)
{
			if(e.getSource()==jbcompile)
		{
			str="";
			if(!jtf.getText().equals(""))
                {
                	
                	try
                	{
                		fname=jtf.getText().trim()+".java";
                		FileWriter fw=new FileWriter(fname);
                		String s1=jta.getText();
                		PrintWriter pw=new PrintWriter(fw);
                		pw.println(s1);
                		pw.flush();
                                

              		Process error=r.exec("javac D:\\jdk1.8.0_181\\bin\\H.java");
                	BufferedReader err=new BufferedReader(new InputStreamReader(error.getErrorStream()));
                	while(true)
                	{System.out.println("while");
                		String temp=err.readLine();
                		if(temp!=null)
                		{
                			result3+=temp;
                			result3+="/n";
                		}
                		else{
                                break;
                               }
                     }

                		 if(result3.equals(""))
                		{System.out.println("hello");
                			jta1.setText("compilaation successful:"+fname);
                			err.close();

                		}
                        		else {jta1.setText(result3);
}


                	}               	
                	catch(Exception e4)
                	{
                		System.out.println(e4);
                	}
                	
	
             }
			else jta1.setText("please enter the java program name!");
		}
		else if(e.getSource()==jbrun)
			
		{int start=0;
		try
		{
			String fn=jtf.getText().trim();
			Process p=r.exec("java H");
			BufferedReader output=new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader error=new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while(true)
			{
				String temp=output.readLine();
				if(temp!=null)
				{
					result+=temp;
					result+="\n";
					
				}
				else
				{
					break;
				}
			}
			while(true)
			{
				String temp=error.readLine();
				if(temp!=null)
				{
					result1+=temp;
					result1+="\n";
				}
				else
				{
					break;
				}
			}
			output.close();
			error.close();
			jta1.setText(result+"\n"+result1);
		}
		catch(Exception e2)
		{
			System.out.println(e2);
		}
			
			
		}
		}
		
	
	public static void main(String... args)
	{
		new MyEditor();
	}
	
	
}
class MyFocusListener extends FocusAdapter
{
	MyEditor e;
	MyFocusListener(MyEditor e)
	{
		this.e=e;
		
	}
	public void focusGanined(FocusEvent fe)
	{System.out.println("focus");
		String str=e.jtf.getText().trim();
		e.jta.setText("public class "+str+"\n"
				+"{"+"\n"
				+"public static void main(String... args)"+"\n"
				+"{"+"\n"
				+"               "+"\n"
				+"}"+"\n"
				+"}");
		
	}
}