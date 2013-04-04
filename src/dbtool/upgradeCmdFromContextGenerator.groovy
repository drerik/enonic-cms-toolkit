#!/usr/bin/env groovy

/**
 * Created by Erik Sunde ( esu At enonic.com ) April 2013
 * Script to generate a dbtool upgrade command for 4.4 and 4.5
 */


def contextParser( def contextFilename, def cmsHomeDir)
{
    def context = new XmlParser().parse(new File(contextFilename))
    def resource = context.get("Resource")

    println("./upgradetool.sh -a "+resource."@username"[0]+":"+resource."@password"[0] +" -d org.postgresql.Driver -u \""+resource."@url"[0] +"\" -h "+cmsHomeDir+" -o check")
}

def help()
{
    println("This script creates a scriptfile for cms-shell to configure a jdbc connections, set upgrade parameters and do a 'upgrade check'")
    println("Usage:")
    println("upgradeCmdFromContextGenerator.groovy <context.xml> <cms.home dir>")
    System.exit(2)
}

if ( args.length == 2 && new File(args[0]).isFile() && new File(args[1]).isDirectory() )
{
    contextParser(args[0], args[1])
} else {
    help()
}

