//
//  WidgetFactory.java
//  
//
//  Created by Group Four on Nov 4, 2008
//  Copyright 2008 Group Four. All rights reserved.
//

package Widgets;

import Widgets.*;

import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.jar.*;

import javax.swing.ImageIcon;

/**
  Widget factory provides widget creation by widget name given as a string.  
  Will also build and store list of ImageIcons for quick access.
  */
public class WidgetFactory
{

    /**
        Basic filter class to grab all .class files from a file path.
      */
    private static class ClassFileFilter implements FilenameFilter
    {
        public boolean accept(File dir, String name)
        {
            return name.endsWith(".class");
        }
    }

    /* Leaving this block of widget names in for now.
       I'm not sure we should continue using them when the widgets
       are found via dynamic search rather than hardcoding.  Leaving these
       as contants is somewhat misleading and inconsistent when some widgets
       have hardcoded symbols and some don't. --Andrew */
    public final static String BOMBWIDGET = "BombWidget";
    public final static String CONVVEYORBELT = "ConveyorBelt";
    public final static String MYSTERYBOX = "MysteryBox";
    public final static String SIMPLEBOX = "SimpleBox";
    public final static String TVWIDGET = "TVwidget";
    public final static String RAMP = "Ramp";
    public final static String GORILLA = "Gorilla";
    public final static String CCBOX = "CCBox";
    
    /* 
       table of classes;
       key = class name
       val = URL string for the class
    */
    private static Hashtable<String, Class> classTable;
    
    /*
       table of icons;
       key = class name
       val = the ImageIcon
    */
    private static Hashtable<String, ImageIcon> iconTable;

	/* indicates whether tables have been initialized */
	private static boolean initialized = false;

    /**
      Initialize the widget lists, combing the widgets folder and jar file for all class and adding
      the names of any that are found to be implementing the widget API into the class and icon tables.

      This method will actively search both of these locations for classes so that this method doesn't
      need to be hardcoded to fill in the list.
     */
    private static void init()
    {
        /* double check init hasn't been run already */
        if(initialized == true)
            return;
        
        /* set up class table */
        classTable = new Hashtable<String, Class>();
        buildClassTable();
        
        /* set up icon table */
        iconTable = new Hashtable<String, ImageIcon>();
        buildIconTable();

		initialized = true;
    }

    /**
        Build class table.
        -search classpath entries
        -for directories:
            1. check for Widgets subdir
            2. look through all class files
            3. check for Widgets.Widget implementation
            
        -for files:
            -if jar file:
                1. look through class files
                2. match against any Widgets/*.class
                3. check for Widgets.Widget implementation
      */
    private static void buildClassTable()
    {
        String pathSeparator = System.getProperty("path.separator");
        String classPath = System.getProperty("java.class.path");

//        System.out.println("Full classpath: " + classPath);

        StringTokenizer strtok = new StringTokenizer(classPath, pathSeparator);

        while(strtok.hasMoreTokens())
        {
            String path = strtok.nextToken();
//            System.out.println("\nPath token: " + path);

            File pathFile = new File(path);
            
            if(pathFile.isDirectory())
            {
//                System.out.println("> Directory");
                searchDirectory(pathFile);
            }
            else
            {
                /* pathfile is not a directory
                   -> if a jar file, check contents for classfiles */
//                System.out.println("> File");

                /* match against *.jar */
                if(path.endsWith(".jar"))
                {
//                    System.out.println(" > looks like a jar");
                    try
                    {
                        JarFile jar = new JarFile(path);
                        
//                        System.out.println("  > jar file contents:");
                        searchJar(jar);
                    }
                    catch(IOException e)
                    {
                        System.err.println("Error opening file as jar: \'" + path + "\', " + e);
                    }
                }
                else
                {
//                    System.out.println(" > doesn't look like a jar");
                }
            }
        }
    }


    /**
      Search directory in filePath for classfiles in a Widgets subdir.
      */
    private static void searchDirectory(File filePath)
    {
        /* check if this path has a widgets package subdir */
        String widgetPath = new String(filePath.getName() + File.separator + "Widgets" + File.separator);
        File widgetPathFile = new File(widgetPath);

//        System.out.println(" > looking in: " + widgetPath);

        /* if the widgets subfolder doesn't exist in this directory, don't look any further */
        if(!widgetPathFile.exists())
            return;

        /* go through list of classfiles in directory */
//        System.out.println("  > contents:");
        for(String className : widgetPathFile.list(new ClassFileFilter()))
        {
//            System.out.println("  > " + className);
            
            /* remove .class extension */
            String classNameNoExtension = new String(className.substring(0,className.lastIndexOf(".class")));
//            System.out.println("   > " + classNameNoExtension);

            /* check if its a widget */
            try
            {
                Class c = Class.forName("Widgets." + classNameNoExtension);

//                System.out.println("    > interfaces:");

                for(Class i : c.getInterfaces())
                {
//                    System.out.println("     > " + i.getName());

                    if(i.getName().equals("Widgets.Widget"))
                    {
//                        System.out.println("      > it's a widget!");

                        addClass(classNameNoExtension);
                    }
                }
            }
            catch(ClassNotFoundException e)
            {
                /* probably won't run into classnotfound since we're looking in class files */
                System.err.println("Error: \'" + widgetPathFile + File.separator + className + "\' not actually found.");
            }
            catch(ClassFormatError e)
            {
                /* will more often find classformaterror since we're just looking at .class files blindly in "[classpath]/Widgets/" */
                System.err.println("Error: \'" + widgetPathFile + File.separator + className + "\' is a malformed classfile.");
            }
        }
    }

    private static void searchJar(JarFile jar)
    {
        /* cycle through the jar file's entries */
        for(Enumeration<JarEntry> entries = jar.entries(); entries.hasMoreElements();)
        {
            JarEntry entry = entries.nextElement();
//            System.out.println("   > " + entry.getName());

            /* match any .class file in Widgets/ */
            if(entry.getName().matches("Widgets/.+\u002Eclass"))
            {
//                System.out.println("    > looks like a class file");

                /* pull class name between the last "/" and last "." */
                String className = entry.getName().substring(
                        entry.getName().lastIndexOf("/") + 1,
                        entry.getName().lastIndexOf("."));
//                System.out.println("     > class name is \'" + className + "\'");

                /* see if it's a widget */
                try
                {
                    Class c = Class.forName("Widgets." + className);
  
//                    System.out.println("      > interfaces:");
  
                    for(Class i : c.getInterfaces())
                    {
//                        System.out.println("       > " + i.getName());
  
                        if(i.getName().equals("Widgets.Widget"))
                        {
//                            System.out.println("        > it's a widget!");

                            addClass(className);
                        }
                    }
                }
                catch(Exception e)
                {
                    /*TODO exception handling */
                }
            }
        }
    }

    /**
        Build Icon Table.
        -take list of classes in class table
        -instantiate each, grab a copy of their image icons
        -put it in the icon table
      */
    private static void buildIconTable()
    {
        /* generate icon table from class table by iterating over the set of keys */
        for (Enumeration<String> e = classTable.keys(); e.hasMoreElements();)
        {
            String cur = e.nextElement();
            
            try
            {
                /* TODO: break this statement into multiple steps to check for nulls, etc */
                iconTable.put( cur, 
                    ((Widget)classTable.get(cur).newInstance()).getIcon());
            }
            catch(Exception exc)
            {
                /* TODO: meaningful error message */
            }
        }
    }

    /**
      Add a class to the class table.
      */
    private static void addClass(String className)
    {
        try
        {
            classTable.put(className, Class.forName("Widgets." + className));
        }
        catch(ClassNotFoundException e)
        {
            System.err.println("Error: WidgetFactory unable to add class \'" + className + "\' to class table: " + e);
        }
    }

    /**
      Create a new widget by class name.
      @param name Name of the appropriate widget class.
      @return Returns a newly instantiated copy of the widget, or null if named widget not found.
      */
    public static Widget createWidget(String name)
    {
        /* make sure factory is initialized */
        if(initialized == false)
            init();

        Widget w = null;
        Class c;

        /* try to get the class from the class table */
        c = classTable.get(name);

        /* if class wasn't null, try to instantiate */
        if (c != null)
        {
            try
            {
                w = (Widget)classTable.get(name).newInstance();
            }
            catch(Exception e)
            {
                /* TODO: better error message / handling */
                System.err.println(">>> Error, failed to create new instance of \'" + name + "\': " + e);

                w = null;
            }
        }

        return w;
    }

    /**
      Get the ImageIcon associated with a widget class name.
      @param name Name of the widget class.
      @return Returns the image icon, or null if named widget not found.
      */
    public static ImageIcon getIcon(String name)
    {
        /* make sure factory is initialized */
        if(initialized == false)
            init();

        return iconTable.get(name);
    }


    /**
      Return a set of all widgets available in the factory.
      @return Returns a set of strings naming the widget classes the factory knows about.
      */
    public static Set<String> availableWidgets()
    {
        /* make sure factory initialized */
        if(initialized == false)
            init();

        return classTable.keySet();
    }


}

