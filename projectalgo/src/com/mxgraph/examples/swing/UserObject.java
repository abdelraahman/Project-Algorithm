package com.mxgraph.examples.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxDomUtils;
import com.mxgraph.view.mxGraph;

public class UserObject extends JFrame
{

	private static final long serialVersionUID = -708317745824467773L;
	 protected static mxGraph graph;
	 static int row=1000;
	 static int col=20;
	 static int click=0;
	 static ArrayList <Edge> result = new ArrayList <Edge> ();;
	 static boolean check = false;
	 int number=3;
	 public ArrayList<Edge> edgeList = new ArrayList<Edge>();
	 public static ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
	 protected static HashMap list = new HashMap();
	 protected static HashMap<Edge,Object> edgeObjectList = new HashMap<Edge,Object>();
	 public  ArrayList<Edge> edgelistdij= new ArrayList<Edge>();
	public static mxGraph getGraph() {
		return graph;
	}
	public Edge findOriginalEdge(Edge e)
    {
        for(int i = 0;i < edgeList.size();i++)
        {
        
            Edge e1 = edgeList.get(i);
            System.out.println("dddd "+e.startVertex.name+" " +e1.startVertex.name+" "+e1.endVertex.name+" "+
                    e.endVertex.name);
            if(e.startVertex.name.equals(e1.startVertex.name) &&
                e.endVertex.name.equals(e1.endVertex.name))
                return e1;
        }
        return null;
    }
	public boolean isInt(String str){
	    return (str.lastIndexOf("-") == 0 && !str.equals("-0")) ? str.substring(1).matches(
	            "\\d+") : str.matches("\\d+");
	}
    public static Object findEdgeUsingEndVertex(Vertex x)
    {
        for(Edge edge : edgeObjectList.keySet())
        {
            if(edge.endVertex.name.equals(x.name))
                return edgeObjectList.get(edge);
        }
        return null;
    }
	
	 static Vertex findVertex(String name)
	    {
	        for(int i = 0; i < vertexList.size(); i++)
	        {
	            if(vertexList.get(i).name.equals(name))
	                return vertexList.get(i);
	        }
	        return null;
	    }
	 public void deleteAllEdges()
	    {
		 graph.getModel().beginUpdate();
	        for(Edge e1 :edgeObjectList.keySet())
	        {
	        	
	        graph.clearSelection();
	        graph.addSelectionCell(edgeObjectList.get(e1));
	        graph.removeCells();
	   
	        }
	        edgeObjectList.clear();
	     	graph.getModel().endUpdate();
	    }
	 static Edge findEdge(Edge edge)
	    {
	        for(Edge e : edgeObjectList.keySet())
	            if(e.startVertex.name.equals(edge.startVertex.name) &&
	                    e.endVertex.name.equals(edge.endVertex.name)
	                    )
	                return e;
	        return null;
	    }
	private Object cell;
	public static HashMap getlist() {
		return list;
	}
	public UserObject()
	{
		super("Hello, World!");

		
		Document doc = mxDomUtils.createDocument();
		Element person1 = doc.createElement("Person");
		person1.setAttribute("firstName", "Daffy");
		//person1.setAttribute("lastName", "Duck");

		Element person2 = doc.createElement("Person");
		person2.setAttribute("firstName", "Bugs");
		//person2.setAttribute("lastName", "Bunny");

		Element relation = doc.createElement("Knows");
		relation.setAttribute("since", "1985");
		

		 graph = new mxGraph()
		{
			// Overrides method to disallow edge label editing
			public boolean isCellEditable(Object cell)
			{
				return !getModel().isEdge(cell);
			}

			// Overrides method to provide a cell label in the display
			public String convertValueToString(Object cell)
			{
				if (cell instanceof mxCell)
				{
					Object value = ((mxCell) cell).getValue();

					if (value instanceof Element)
					{
						Element elt = (Element) value;

						if (elt.getTagName().equalsIgnoreCase("person"))
						{
							String firstName = elt.getAttribute("firstName");
							String lastName = elt.getAttribute("lastName");

							if (lastName != null && lastName.length() > 0)
							{
								return lastName + ", " + firstName;
							}

							return firstName;
						}
						else if (elt.getTagName().equalsIgnoreCase("knows"))
						{
							return elt.getTagName() + " (Since "
									+ elt.getAttribute("since") + ")";
						}

					}
				}

				return super.convertValueToString(cell);
			}

			// Overrides method to store a cell label in the model
			public void cellLabelChanged(Object cell, Object newValue,
					boolean autoSize)
			{
				if (cell instanceof mxCell && newValue != null)
				{
					Object value = ((mxCell) cell).getValue();

					if (value instanceof Node)
					{
						String label = newValue.toString();
						Element elt = (Element) value;

						if (elt.getTagName().equalsIgnoreCase("person"))
						{
							int pos = label.indexOf(' ');

							String firstName = (pos > 0) ? label.substring(0,
									pos).trim() : label;
							String lastName = (pos > 0) ? label.substring(
									pos + 1, label.length()).trim() : "";

							// Clones the value for correct undo/redo
							elt = (Element) elt.cloneNode(true);

							elt.setAttribute("firstName", firstName);
							elt.setAttribute("lastName", lastName);

							newValue = elt;
						}
					}
				}

				super.cellLabelChanged(cell, newValue, autoSize);
			}
		};

		Object parent = graph.getDefaultParent();
		
//______________________________________________
		Label labN = new Label("Vertex Name");
		Label Edgelab = new Label("Edge");
		Label labV = new Label("Edge Weight");
		Label labFrom = new Label("From Vertex");
		Label labTo = new Label("To Vertex");
		Label srcDij = new Label("Source");
		Label srcMaxflow = new Label("Source");
		Label disMaxflow = new Label("Destination");
		Label clicklab = new Label("Click Again");
		Label finalResult = new Label("Final Result");
		Label resultOfMaxflow = new Label("");
		Label resultlab = new Label("Result");
		
		Label Empty = new Label("Empty!!");
		Label alreadyfound = new Label("Vertex already found!");
		
		Label Error = new Label("Vertex not Found,Try Again!!");
		Label num = new Label("Not a number!!");

		
		
		Font f = new Font("TimesRoman",Font.BOLD,16);
		Font f1= new Font("TimesRoman",Font.BOLD,25);
		Font f2= new Font("TimesRoman",Font.BOLD,20);
		
		labN.setFont(f);
		labV.setFont(f);
		labFrom.setFont(f);
		labTo.setFont(f);
		srcDij.setFont(f);
		srcMaxflow.setFont(f);
		disMaxflow.setFont(f);
		Edgelab.setFont(f1);
		clicklab.setFont(f2);
		finalResult.setFont(f2);
		resultOfMaxflow.setFont(f1);
		resultlab.setFont(f2);
		Error.setFont(f2);
		Empty.setFont(f2);
		alreadyfound.setFont(f2);
		num.setFont(f2);
		clicklab.setVisible(false);
		finalResult.setVisible(false);
		Error.setVisible(false);
		Empty.setVisible(false);
		alreadyfound.setVisible(false);
		num.setVisible(false);
		
		clicklab.setForeground(Color.blue);
		finalResult.setForeground(Color.red);
		Error.setForeground(Color.red);
		Empty.setForeground(Color.red);
		alreadyfound.setForeground(Color.red);
		num.setForeground(Color.red);
		
		labN.setBounds(10,10, 150,30);
		labV.setBounds(10,200, 100,30);
		Edgelab.setBounds(100,130, 150,30);
		labFrom.setBounds(10,250, 150,30);
		labTo.setBounds(10,300, 150,30);
		srcDij.setBounds(10,450, 100,30);
		srcMaxflow.setBounds(10,550, 100,30);
		disMaxflow.setBounds(10,600, 100,30);
		clicklab.setBounds(300,500,150,30);
		finalResult.setBounds(300,500,150,30);
		resultOfMaxflow.setBounds(200,660,100,30);
		resultlab.setBounds(10,660,150,30);
		
		JTextField tf=new JTextField(); 
		JTextField tfFrom=new JTextField(); 
		JTextField tfTo=new JTextField(); 
		JTextField tfVertex=new JTextField(); 
		JTextField dijsrc=new JTextField();
		JTextField srcMax=new JTextField(); 
		JTextField dest=new JTextField(); 
		
	   tf.setBounds(120,10, 150,30); 
	    tfFrom.setBounds(120,250, 150,30); 
	    tfTo.setBounds(120,300, 150,30); 
	    tfVertex.setBounds(120,200, 150,30);
	    dijsrc.setBounds(120,450, 150,30);
	    srcMax.setBounds(120,550, 150,30);
	    dest.setBounds(120,600, 150,30);
	    
	    JButton buttonNode=new JButton("Add Vertex");  
	    buttonNode.setBounds(120,60,150,30);  
	    buttonNode.setFont(f);
	    JButton buttomEdge=new JButton("Add Edge");  
	    buttomEdge.setBounds(120,350,150,30);  
	    buttomEdge.setFont(f);


	    
	    
	    JButton dijkestra=new JButton("Dijkestra");  
	    dijkestra.setBounds(120,500,150,30);  
	    dijkestra.setFont(f);
	    
	    JButton maxFlow=new JButton("MaxFlow");  
	    maxFlow.setBounds(120,700,150,30);  
	    maxFlow.setFont(f);
	    
	    
	    JButton deleteAll=new JButton("Delete All");  
	    deleteAll.setBounds(10,750,150,30);  
	    deleteAll.setFont(f);
	    
	    

	    
	    
	    buttonNode.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){  
	           // AddNode add = new AddNode(tf.getText().toString());
	    		Vertex vertex = new Vertex(tf.getText());
	    		if(tf.getText().length() == 0) {
	    			Empty.setBounds(300,10, 300,30);
	    			alreadyfound.setVisible(false);
	    			Empty.setVisible(true);
	    		}
	    		else if(list.get(tf.getText())!= null)
	    		{
	    			alreadyfound.setBounds(300,10, 300,30);
	    			Empty.setVisible(false);
	    			alreadyfound.setVisible(true);
	    			
	    		}
	    		else {
	    		Empty.setVisible(false);
	    		alreadyfound.setVisible(false);
	    		graph.getModel().beginUpdate();
	    		Object v1 = graph.insertVertex(parent, null, tf.getText(), row, col, 70, 30,"ROUNDED;strokeColor=black;fillColor=blue");
	    		vertexList.add(vertex);
	    		list.put(tf.getText(), v1);
	 
	    		if(click >=3) {
	    			row-=100;
	    			col+=30+(number*25);
	    			--number;
	    			
	    		}
	    		else {
	    		row+=100;
	    		col+=30+(click*50);
	    		}
	    		click++;
	    		tf.setText("");
	    		graph.getModel().endUpdate();
	        }  
	    	}
	    });  
	    
	    buttomEdge.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){  
	           // AddNode add = new AddNode(tf.getText().toString());
	    	
	    		Object v1 = list.get(tfFrom.getText());
	    		Object v2 = list.get(tfTo.getText());
	    		Error.setVisible(false);
	    		num.setVisible(false);
	    		if(v1 == null)
	    		{
	    			Error.setBounds(300,250, 300,30);
	    			Error.setVisible(true);
	    		}
	    		else if (v2 == null)
	    		{
	    			Error.setBounds(300,300, 300,30);
	    			Error.setVisible(true);
	    		}
	    		else if(isInt(tfVertex.getText()) == false)
	    		{
	    			num.setBounds(300,200, 300,30);
	    			num.setVisible(true);
	    		}
	    		else {
			    	graph.getModel().beginUpdate();
			    	Error.setVisible(false);
			    	num.setVisible(false);
			    	Object v3 = graph.insertEdge(parent,null, tfVertex.getText(), v1, v2);
			    	 Vertex vFrom = findVertex(tfFrom.getText());
			    	 Vertex vTo= findVertex(tfTo.getText());
			    	 Edge arr = new Edge(vFrom,vTo,Integer.parseInt(tfVertex.getText()));
			    	 vFrom.edges.add(arr);
			    	 edgeObjectList.put(arr, v3);
			    	 edgeList.add(arr);
				     tfFrom.setText("");
				     tfTo.setText("");
				     tfVertex.setText("");
			    	graph.getModel().endUpdate();
	        }  
	    	}
	    }); 

	    
	    dijkestra.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){  
	    			//------------------------------------------------
	    		Vertex src = findVertex(dijsrc.getText());
	    		if(src == null)
	    		{
	    			Error.setBounds(300,450, 300,30);
	    			Error.setVisible(true);
	    			
	    		}else {
	    			
		    		if(check == false) {
		    			clicklab.setVisible(true);
		    			Error.setVisible(false);
			    		deleteAllEdges();
			    		edgelistdij = Dijkestra.dijkstra(vertexList, src, vertexList.size());    		
			    		check = true;
			    		clicklab.setBounds(300,500,150,30);
			    		finalResult.setBounds(300,500,150,30);
			    		}else {
			                graph.getModel().beginUpdate();
			                Error.setVisible(false);
			                Edge e1 = edgelistdij.get(0);
			                Object v1 = list.get(e1.startVertex.name);
			                Object v2 = list.get(e1.endVertex.name);
			                Object v3 = graph.insertEdge(parent, null, String.valueOf(e1.weight), v1, v2);
			                Object oldEdge = findEdgeUsingEndVertex(e1.endVertex);
			                edgeObjectList.put(e1,v3);
			                if(oldEdge != null)
			                {
			                    graph.clearSelection();
			                    graph.addSelectionCell(oldEdge);
			                    graph.removeCells();
			                }
			                
			                edgelistdij.remove(0);
			                if(edgelistdij.size()==0)
			                {
			                	dijkestra.setEnabled(false);
			                	clicklab.setVisible(false);
			                	finalResult.setVisible(true);
			                }
			                graph.getModel().endUpdate();
			    		}
	    			//------------------------------------------------
	    		}
	    	}
	    }
	         
	    );  
	    
	    

	    
	    //-----------------------------------------------
	    // to delete Node
	    deleteAll.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){  
	    	//	graph.getModel().remove(cell);
	            //graph.selectChildCell();
	    		graph.selectAll();
	            graph.removeCells();
	            edgeList.clear();
	            vertexList.clear();
	            edgeObjectList.clear();
	            list.clear();
	            edgelistdij.clear();
	       
	            result.clear();
	       	  row=1000;
	    	  col=20;
	    	  click=0;
	    	  clicklab.setVisible(false);
	    	  finalResult.setVisible(false);
	    	  check = false;
	    	  dijkestra.setEnabled(true);
	    	  maxFlow.setEnabled(true);
	    	  dijsrc.setText("");
	    	  srcMax.setText("");
	    	  dest.setText("");
	    	  resultOfMaxflow.setText("");
	    	  Error.setVisible(false);
	    	  
	    		//graph.removeCells();
	    	
	        }  
	    }); 
	    //-----------------------------------------------------------
	    maxFlow.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){ 
	    		Vertex src = findVertex(srcMax.getText());
	    		Vertex des = findVertex(dest.getText());
	    		if(src == null)
	    		{
	    			Error.setBounds(300,550, 300,30);
	    			Error.setVisible(true);
	    		}
	    		else if(des == null)
	    		{
	    			Error.setBounds(300,600, 300,30);
	    			Error.setVisible(true);
	    		}
	    		else {
	    		if(check == false) {
	    		deleteAllEdges();
	    		Error.setVisible(false);
	    		int x = MaxFlow.fordFulkerson(vertexList, vertexList.indexOf(src), vertexList.indexOf(des), result);
	    		resultOfMaxflow.setText(String.valueOf(x));
	    		check = true;
	    		clicklab.setBounds(300,700,150,30);
	    		clicklab.setVisible(true);
	    	
	    		}else {
	    			
	    			Edge e1 = result.get(0);
	    			if(findEdge(e1) != null)
	    			{
	    				graph.clearSelection();
	                    graph.addSelectionCell(edgeObjectList.get(findEdge(e1)));
	                    graph.removeCells();
	    			}
	    			Edge e2 = findOriginalEdge(e1);
	    			System.out.println(e2.startVertex+" "+e2.endVertex+" ");
	    			graph.getModel().beginUpdate();
	    			Object v = graph.insertEdge(parent, null, String.valueOf(e1.weight), list.get(e2.startVertex.name), list.get(e2.endVertex.name));
	    			edgeObjectList.put(e1, v);
	    			graph.getModel().endUpdate();
	    			result.remove(0);
	                if(result.size()==0)
	                {
	                	maxFlow.setEnabled(false);
	                	clicklab.setVisible(false);
	                	finalResult.setBounds(300,700,150,30);
	                	finalResult.setVisible(true);
	                }
	    			
	    		}
	    		
	    		
	    		
	    		
	    	
	        }  
	    	}
	    });
	    
	    //----------------------------------------
	    
	    getContentPane().add(tf);
	    getContentPane().add(tfFrom);
	    getContentPane().add(tfTo);
	    getContentPane().add(buttonNode);
	    getContentPane().add(labN);
	    getContentPane().add(labV);
	    getContentPane().add(labFrom);
	    getContentPane().add(labTo);
	    getContentPane().add(buttomEdge);
	    getContentPane().add(tfVertex);
	    getContentPane().add(deleteAll);
	    getContentPane().add(dijkestra);
	    getContentPane().add(srcDij);
	    getContentPane().add(dijsrc);
	    getContentPane().add(maxFlow);
	    getContentPane().add(srcMaxflow);
	    getContentPane().add(disMaxflow);
	    getContentPane().add(dest);
	    getContentPane().add(Edgelab);
	    getContentPane().add(srcMax);
	    getContentPane().add(clicklab);
	    getContentPane().add(finalResult);
	    getContentPane().add(resultOfMaxflow);
	    getContentPane().add(resultlab);
	    getContentPane().add(Error);
	    getContentPane().add(Empty);
	    getContentPane().add(alreadyfound);
	    getContentPane().add(num); 
	    
	    
	    
			
//_________________________________________________
		graph.getModel().beginUpdate();
		try
		{
			//Object v1 = graph.insertVertex(parent, null, person1, 300, 20, 80,
			//		30);
		//	Object v2 = graph.insertVertex(parent, null, person2, 400, 150, 80,
			//		30);
			//graph.insertEdge(parent, null, relation, v1, v2);
		}
		finally
		{
			graph.getModel().endUpdate();
		}

		// Overrides method to create the editing value
		mxGraphComponent graphComponent = new mxGraphComponent(graph)
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 6824440535661529806L;

			public String getEditingValue(Object cell, EventObject trigger)
			{
				if (cell instanceof mxCell)
				{
					Object value = ((mxCell) cell).getValue();

					if (value instanceof Element)
					{
						Element elt = (Element) value;

						if (elt.getTagName().equalsIgnoreCase("person"))
						{
							String firstName = elt.getAttribute("firstName");
							String lastName = elt.getAttribute("lastName");

							return firstName + " " + lastName;
						}
					}
				}

				return super.getEditingValue(cell, trigger);
			};

		};
		
		getContentPane().add(graphComponent);
		
		// Stops editing after enter has been pressed instead
		// of adding a newline to the current editing value
		graphComponent.setEnterStopsCellEditing(true);
		//----------------------------------------------
		//      to know which node to delete
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e)
			{
				cell = graphComponent.getCellAt(e.getX(), e.getY());
				
			}
		});
		//--------------------------------------------------
	}

	public static void main(String[] args)
	{
		UserObject frame = new UserObject();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1500, 1000);
		frame.setVisible(true);
		
	}


}