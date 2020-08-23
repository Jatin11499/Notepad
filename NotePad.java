package NotePad;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.undo.*;
import java.io.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 
import java.util.*;
import javax.swing.text.*;
import javax.swing.text.Highlighter.HighlightPainter;

class NotePad extends JFrame
{
	Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Dell\\Documents\\JCreator Pro\\MyProjects\\NotePad\\np.png");
	JMenuBar mb;
	JMenu file,edit,format,view,help;
	JMenuItem newdoc,open,save,saveas,pagesetup,print,exit,undo,cut,copy,paste,del,find,findnext,replace,gt,selectall,timedate,ft,viewhelp,about;
	JCheckBoxMenuItem wordwrap,statusbar;
	JTextArea ta;
	JScrollPane sp;
	JLabel l;
	UndoManager un;
	JDialog frDialog,rDialog,fontDialog;
	JButton btnFind,btnReplace,btnReplaceAll,btnFindNext1,btnCancel,btnFind1,btnOK,btnCancel1;
	static int p0,p1;
	Choice c1,c2,c3;
	boolean isSaved = false;
	KeyStroke keyStrokeToOpen,keyStrokeToNew,keyStrokeToSave,keyStrokeToUndo,keyStrokeToCut,keyStrokeToCopy,keyStrokeToPaste,keyStrokeToDelete,keyStrokeToPrint;
	public NotePad()
	{
		setIconImage(icon);
		mb = new JMenuBar();
		file = new JMenu("File");
		edit = new JMenu("Edit");
		format = new JMenu("Format");
		view = new JMenu("View");
		help = new JMenu("Help");
		newdoc = new JMenuItem("New");
		keyStrokeToNew= KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK);
		newdoc.setAccelerator(keyStrokeToNew);
		open = new JMenuItem("Open...");
		keyStrokeToOpen= KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
		open.setAccelerator(keyStrokeToOpen);
		save = new JMenuItem("Save");
		keyStrokeToSave= KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
		save.setAccelerator(keyStrokeToSave);
		saveas = new JMenuItem("Save As...");
		pagesetup = new JMenuItem("Page Setup...");
		pagesetup.setEnabled(false);
		print = new JMenuItem("Print...");
		keyStrokeToPrint= KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK);
		print.setAccelerator(keyStrokeToPrint);
		exit = new JMenuItem("Exit");
		undo = new JMenuItem("Undo");
		keyStrokeToUndo= KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK);
		undo.setAccelerator(keyStrokeToUndo);
		cut = new JMenuItem("Cut");
		keyStrokeToCut= KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK);
		cut.setAccelerator(keyStrokeToCut);
		copy = new JMenuItem("Copy");
		keyStrokeToCopy= KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK);
		copy.setAccelerator(keyStrokeToCopy);
		paste  = new JMenuItem("Paste");
		keyStrokeToPaste= KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK);
		paste.setAccelerator(keyStrokeToPaste);
		del = new JMenuItem("Delete");
		keyStrokeToDelete= KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
		del.setAccelerator(keyStrokeToDelete);
		find = new JMenuItem("Find...");
		findnext = new JMenuItem("Find Next");
		replace = new JMenuItem("Replace...");
		gt = new JMenuItem("Go To...");
		selectall = new JMenuItem("Select All");
		timedate = new JMenuItem("Time/Date");
		wordwrap = new JCheckBoxMenuItem("Wordwrap");
		ft = new JMenuItem("Font...");
		statusbar = new JCheckBoxMenuItem("Status Bar",true);
		viewhelp = new JMenuItem("View Help");
		viewhelp.setEnabled(false);
		about = new JMenuItem("About Notepad");
		ta = new JTextArea(39,121);
		sp = new JScrollPane(ta);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
   		LocalDateTime now = LocalDateTime.now();
   		l = new JLabel("|  Ln 1, Col 1                    ");
   		l.setHorizontalAlignment(JLabel.RIGHT);
   		l.setVerticalAlignment(JLabel.BOTTOM);
   		un = new UndoManager();
   		
   		JPopupMenu popupmenu = new JPopupMenu("Edit"); 
   		JMenuItem undop = new JMenuItem("Undo");  
        JMenuItem cutp = new JMenuItem("Cut");  
        JMenuItem copyp = new JMenuItem("Copy");  
        JMenuItem pastep = new JMenuItem("Paste");
        JMenuItem delp = new JMenuItem("Delete");  
        JMenuItem selectallp = new JMenuItem("Select All");  
        popupmenu.add(undop); popupmenu.addSeparator(); popupmenu.add(cutp); popupmenu.add(copyp); popupmenu.add(pastep); popupmenu.add(delp); popupmenu.addSeparator(); popupmenu.add(selectallp);       
        ta.addMouseListener(new MouseAdapter() {  
        	public void mouseReleased(MouseEvent e) {
        		if(e.isPopupTrigger())
        		{         
	            	popupmenu.show(e.getComponent(), e.getX(), e.getY());
	            	undop.addActionListener(ae -> {
			        	try{un.undo();}
			        	catch(Exception ex){}
			        	});
	            	cutp.addActionListener(ae ->{ta.cut();});
	        		copyp.addActionListener(ae ->{ta.copy();});
	        		pastep.addActionListener(ae ->{ta.paste();});
	        		delp.addActionListener(ae ->{ta.setText("");});
	        		selectallp.addActionListener(ae ->{ta.selectAll();});
        		}
           	}                 
        });  
   		
   		ta.addKeyListener(new KeyAdapter(){
   		public void keyReleased(KeyEvent e)
   		{
   			String data = ta.getText();
   			String line[] = data.split("\\n");
   			for(int i=0;i<line.length;i++)
   			{
   				String cols = line[i];
   				l.setText("|  Ln "+line.length+", Col "+(cols.length()+1)+"                    ");
   			}
   		}});
        
        open.addActionListener(e ->{
        	isSaved = true;
        	JFileChooser fc=new JFileChooser();    
		    int i=fc.showOpenDialog(this);    
		    if(i==JFileChooser.APPROVE_OPTION){    
		        File f=fc.getSelectedFile();    
		        String filepath = f.getPath();
		        String fn =  f.getName();
		        setTitle(fn+" - Notepad");   
		        try{  
		        BufferedReader br=new BufferedReader(new FileReader(filepath));    
		        String s1="",s2="";                         
		        while((s1=br.readLine())!=null){    
		        s2+=s1+"\n";    
		        }    
		        ta.setText(s2);    
		        br.close();    
		        }catch (Exception ex) {ex.printStackTrace();  }                 
		    }
        	});
        	
        save.addActionListener(e ->{
        	JFileChooser fc=new JFileChooser();
	        fc.setDialogTitle("Specify a file to save");   
			int i=fc.showSaveDialog(this);    
			if(i==JFileChooser.APPROVE_OPTION)
			{    
				File f=fc.getSelectedFile();
			    if(f==null)
			    {
			       return;
			    }
			    if(!f.getName().toLowerCase().endsWith(".txt"))
			    {
			        f = new File(f.getParentFile(),f.getName()+".txt");
			    }
			    try
			    {
			        ta.write(new OutputStreamWriter(new FileOutputStream(f),"utf-8"));
			        setTitle(f.getName()+" - Notepad");
			        isSaved = true;
			    }
			    catch(Exception ex)
			    {
			        ex.printStackTrace();
			    } 
			}
        	});
        	
        saveas.addActionListener(e ->{
        	JFileChooser fc=new JFileChooser();
        	fc.setDialogTitle("Specify a file to save");   
		    int i=fc.showSaveDialog(this);    
		    if(i==JFileChooser.APPROVE_OPTION){    
		        File f=fc.getSelectedFile();
		        if(f==null)
		        {
		        	return;
		        }
		        if(!f.getName().toLowerCase().endsWith(".txt"))
		        {
		        	f = new File(f.getParentFile(),f.getName()+".txt");
		        }
		        try
		        {
		        	ta.write(new OutputStreamWriter(new FileOutputStream(f),"utf-8"));
		        	setTitle(f.getName()+" - Notepad");
		        }
		        catch(Exception ex)
		        {
		        	ex.printStackTrace();
		        } 
		    }
        	});
        exit.addActionListener(e -> dispose());
        
        undo.addActionListener(e -> {
        	try{
        		un.undo();
        	}
        	catch(Exception ex){}
        	});
        
        cut.addActionListener(e -> ta.cut());
        
        copy.addActionListener(e -> ta.copy());
        
        paste.addActionListener(e -> ta.paste());
        
        del.addActionListener(e -> ta.setText(""));
        
        selectall.addActionListener(e -> ta.selectAll());
        
        timedate.addActionListener(e -> ta.setText(dtf.format(now)));
        
        about.addActionListener(e ->{
        		JOptionPane.showMessageDialog(this,"This is a project created by Jatin Teckchandani in JAVA.");
        	});
        	
        statusbar.addItemListener(e ->{
        	if(e.getStateChange()==1){l.setVisible(true);}
        	else {l.setVisible(false);}
        });
        
        ta.getDocument().addUndoableEditListener(e -> {
				un.addEdit(e.getEdit());
			});
			
		newdoc.addActionListener(e -> {
			setTitle("Untitled - Notepad");
			ta.setText("");
		});
		
		print.addActionListener(e ->{
			JOptionPane.showMessageDialog(this,"It seems you don't have a printer.","Bad Printer",JOptionPane.INFORMATION_MESSAGE);
			});
        
        frDialog = new JDialog(this);
        frDialog.setLayout(new GridLayout(3,4));
        JTextField txtFind = new JTextField();
        JTextField txtReplace = new JTextField();
        btnFind = new JButton("Find");
        btnReplace = new JButton("Replace");
        btnReplaceAll = new JButton("Replace All");
        frDialog.add(new JLabel("Find: "));
        frDialog.add(txtFind);
        frDialog.add(new JLabel(""));
        frDialog.add(btnFind);
        frDialog.add(new JLabel("Replace with: "));
        frDialog.add(txtReplace);
        frDialog.add(new JLabel(""));
        frDialog.add(btnReplace);
        frDialog.add(new JLabel(""));
        frDialog.add(new JLabel(""));
        frDialog.add(new JLabel(""));
        frDialog.add(btnReplaceAll);
        Highlighter highlighter = ta.getHighlighter();
      	HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.lightGray);
      	
        btnFind.addActionListener(e ->{
        	String data = ta.getText();
        	String text = txtFind.getText();
        	int p0 = text.indexOf(text);
      		int p1 = p0 + text.length();
      		try{
      		highlighter.addHighlight(p0,p1,painter);
      		}
      		catch(Exception ex){}
        	});
        
        btnReplace.addActionListener(e ->{
        	String data = ta.getText();
        	String text = txtFind.getText();
        	String text2 = txtReplace.getText();
        	ta.setText(data.replaceFirst(text,text2));
        	});
        btnReplaceAll.addActionListener(e ->{
        	String data = ta.getText();
        	String text = txtFind.getText();
        	String text2 = txtReplace.getText();
        	ta.setText(data.replace(text,text2));
        	});
        frDialog.pack();
        frDialog.setTitle("Replace");
        
        replace.addActionListener(e -> frDialog.setVisible(true));
        
        rDialog = new JDialog(this);
        rDialog.setLayout(new FlowLayout());
        JTextField txtFind1 = new JTextField(15);
        btnFindNext1 = new JButton("Find Next");
        btnFind1 = new JButton("Find");
        btnCancel = new JButton("Cancel");
        rDialog.add(new JLabel("Find What : "));
        rDialog.add(txtFind1);
        rDialog.add(new JLabel(""));
        rDialog.add(btnFind1);
        rDialog.add(btnFindNext1);
        rDialog.add(btnCancel);
        
        btnFind1.addActionListener(e ->{
        	highlighter.removeAllHighlights();
        	String data = ta.getText();
        	String text = txtFind1.getText();
        	p0 = data.indexOf(text);
      		p1 = p0 + text.length();
      		try{highlighter.addHighlight(p0,p1,painter);}
      		catch(Exception ex){}
        	});
        
        btnFindNext1.addActionListener(e ->{
        	highlighter.removeAllHighlights();
        	String data = ta.getText();
        	String text = txtFind1.getText();
      		
      		while (p0 >= 0) 
      		{
			    p0 = data.indexOf(text,p1);
			    p1 = p0 + text.length();
			    try
			    {highlighter.addHighlight(p0,p1,painter);}
		      	catch(Exception ex){}
		      	break;
      		}
      		if(p0<0)
      			JOptionPane.showMessageDialog(this,"Cannot find :\""+text+"\"");
        	});
        	
        btnCancel.addActionListener(e -> rDialog.setVisible(false));
        rDialog.pack();
        rDialog.setTitle("Find");
        find.addActionListener(e -> rDialog.setVisible(true));
        
        findnext.addActionListener(e -> {
        	highlighter.removeAllHighlights();
        	String data = ta.getText();
        	String text = txtFind1.getText();
      		
      		while (p0 >= 0) 
      		{
			    p0 = data.indexOf(text,p1);
			    p1 = p0 + text.length();
			    try
			    {highlighter.addHighlight(p0,p1,painter);}
		      	catch(Exception ex){}
		      	break;
      		}
      		if(p0<0)
      			JOptionPane.showMessageDialog(this,"Cannot find :\""+text+"\"");
        	});
        
        fontDialog = new JDialog(this);
        fontDialog.setLayout(new FlowLayout());
        c1 = new Choice();
        c1.add("Arial");c1.add("Bookman Old Style");c1.add("Century Gothic");c1.add("Comic Sans MS");c1.add("Times New Roman");
        c2 = new Choice();
        c2.add("Bold");c2.add("Italic");c2.add("Plain");
        c3 = new Choice();
        c3.add("8");c3.add("9");c3.add("10");c3.add("11");c3.add("12");c3.add("14");c3.add("16");
        c3.add("18");c3.add("20");c3.add("22");c3.add("24");c3.add("26");c3.add("28");c3.add("36");c3.add("48");c3.add("72");
        btnOK = new JButton("OK");
        btnCancel1 = new JButton("Cancel");
        fontDialog.add(new JLabel("                "));
        fontDialog.add(new JLabel("Font"));
        fontDialog.add(new JLabel("                   "));
        fontDialog.add(new JLabel("Font Style"));
        fontDialog.add(new JLabel("Font size"));
        fontDialog.add(c1);
        fontDialog.add(c2);
        fontDialog.add(c3);
        fontDialog.add(btnOK);
        fontDialog.add(btnCancel1);
        fontDialog.setTitle("Font");
        fontDialog.setSize(275,125);
        
        btnCancel1.addActionListener(e -> fontDialog.setVisible(false));
        
        btnOK.addActionListener(e -> {
        	String a = c1.getSelectedItem();
        	String b = c2.getSelectedItem();
        	int c = Integer.parseInt(c3.getSelectedItem());
        	
        	if(b=="Bold")
        	{
        		ta.setFont(new Font(a,Font.BOLD,c));
        	}
        	
        	else if(b=="Italic")
        	{
        		ta.setFont(new Font(a,Font.ITALIC,c));
        	}
        	
        	else if(b=="Plain")
        	{
        		ta.setFont(new Font(a,Font.PLAIN,c));
        	}
        	
        	fontDialog.setVisible(false);
        });
        
        ft.addActionListener(e -> fontDialog.setVisible(true));
        
        gt.addActionListener(e -> {
        	try{
	        	String lineno = JOptionPane.showInputDialog(this,"line number :","Go To Line",JOptionPane.PLAIN_MESSAGE);
	        	if(lineno==null){return;}
	        	int ln = Integer.parseInt(lineno);
	        	ta.setCaretPosition(ta.getLineStartOffset(ln-1));
        	}
        	catch(Exception ex){}
        	});
        	
        wordwrap.addItemListener(e -> {
        	if(e.getStateChange()==1)
        	{
        		ta.setLineWrap(true);
        		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        		l.setVisible(false);
        	}
        	else
        	{
        		ta.setLineWrap(false);
        		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        		l.setVisible(true);
        	}
        });
        
		mb.add(file);mb.add(edit);mb.add(format);mb.add(view);mb.add(help);
		file.add(newdoc);file.add(open);file.add(save);file.add(saveas);file.addSeparator();file.add(pagesetup);file.add(print);file.addSeparator();file.add(exit);
		edit.add(undo);edit.addSeparator();edit.add(cut);edit.add(copy);edit.add(paste);edit.add(del);edit.addSeparator();edit.add(find);edit.add(findnext);edit.add(replace);edit.add(gt);edit.addSeparator();edit.add(selectall);edit.add(timedate);
		format.add(wordwrap);format.add(ft);
		view.add(statusbar);
		help.add(viewhelp);help.addSeparator();help.add(about);
		setJMenuBar(mb);
		add(sp,BorderLayout.CENTER);
		add(new JLabel("  "),BorderLayout.EAST);  
		add(new JLabel("  "),BorderLayout.WEST);
		add(l,BorderLayout.SOUTH);
		
		setTitle("Notepad - Jatin");
		setSize(1000,530);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String args[])
	{
    	new NotePad();
	}
}