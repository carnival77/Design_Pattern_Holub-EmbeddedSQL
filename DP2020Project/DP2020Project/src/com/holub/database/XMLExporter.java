/*  (c) 2004 Allen I. Holub. All rights reserved.
 *
 *  This code may be used freely by yourself with the following
 *  restrictions:
 *
 *  o Your splash screen, about box, or equivalent, must include
 *    Allen Holub's name, copyright, and URL. For example:
 *
 *      This program contains Allen Holub's SQL package.<br>
 *      (c) 2005 Allen I. Holub. All Rights Reserved.<br>
 *              http://www.holub.com<br>
 *
 *    If your program does not run interactively, then the foregoing
 *    notice must appear in your documentation.
 *
 *  o You may not redistribute (or mirror) the source code.
 *
 *  o You must report any bugs that you find to me. Use the form at
 *    http://www.holub.com/company/contact.html or send email to
 *    allen@Holub.com.
 *
 *  o The software is supplied <em>as is</em>. Neither Allen Holub nor
 *    Holub Associates are responsible for any bugs (or any problems
 *    caused by bugs, including lost productivity or data)
 *    in any of this code.
 */
package com.holub.database;

import java.lang.*;

import java.io.*;
import java.util.*;

/***
 *	Pass this exporter to a {@link Table#export} implementation to
 *	create a comma-sparated-value version of a {@link Table}.
 *	For example:
 *	<PRE>
 *	Table people  = TableFactory.create( ... );
 *	//...
 *	Writer out = new FileWriter( "people.csv" );
 *	people.export( new CSVExporter(out) );
 *	out.close();
 *	</PRE>
 *	The output file for a table called "name" with
 *	columns "first," "last," and "addrId" would look
 *	like this:
 *	<PRE>
 *	name
 *	first,	last,	addrId
 *	Fred,	Flintstone,	1
 *	Wilma,	Flintstone,	1
 *	Allen,	Holub,	0
 *	</PRE>
 *	The first line is the table name, the second line
 *	identifies the columns, and the subsequent lines define
 *	the rows.
 *
 * @include /etc/license.txt
 * @see Table
 * @see Table.Exporter
 * @see CSVImporter
 */



public class XMLExporter implements Table.Exporter
{	private final Writer out;
	private 	  int	 width;
	
	ArrayList<Object> data_arr = new ArrayList<Object>();
	int a=0;

	public XMLExporter( Writer out )
	{	this.out = out;
	}

	public void storeMetadata( String tableName,
							   int width,
							   int height,
							   Iterator columnNames ) throws IOException

	{	this.width = width;
		String xml_first = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		out.write(xml_first);
		out.write("<XML title= "+tableName+" type=\"xml\">");
		out.write("\n");
//		out.write(tableName == null ? "<anonymous>" : tableName );
//		out.write("&#10;");
		storeRow( columnNames ); // comma separated list of columns ids
//		out.write("</XML>");
	}

	public void storeRow( Iterator data ) throws IOException
	{	
		int i = width;
		while( data.hasNext() )	// row
		{	
			Object datum = data.next(); // element
			
			data_arr.add(datum);
			
		}
		Object last = data_arr.get(0).toString();
		Object first = data_arr.get(1).toString();
		Object addrId = data_arr.get(2).toString();
		
		if(data_arr.size()>3) {
			out.write("<row>");
			out.write("<"+last+"> ");
			out.write(data_arr.get(width*(a+1)).toString());
			out.write(" </"+last+">");
			
			out.write("<"+first+"> ");
			out.write(data_arr.get(width*(a+1)+1).toString());
			out.write(" </"+first+">");
			
			out.write("<"+addrId+"> ");
			out.write(data_arr.get(width*(a+1)+2).toString());
			out.write(" </"+addrId+">");
			out.write("</row>\n");
			
			a++;
		}
		
	}
	

	public void startTable() throws IOException {/*nothing to do*/}
	public void endTable()   throws IOException {/*nothing to do*/}
}
