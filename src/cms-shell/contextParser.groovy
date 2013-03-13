#!/usr/bin/env groovy

/**
 * Created by Erik Sunde ( esu At enonic.com ) March 2013
 * Script to generate a cms-shell to configure a jdbc connections, set upgrade parameters and do a 'upgrade check'
 */


def contextParser( def contextFilename, def cmsHomeDir, def jdbcInitFilename )
{
    def context = new XmlParser().parse(new File(contextFilename))
    def resource = context.get("Resource")

    def jdbcInitFile = new File(jdbcInitFilename)

    jdbcInitFile.write("jdbc add --name database --url \""+resource."@url"[0] +" --user "+resource."@username"[0]+" --password "+resource."@password"[0] +"\n")
    jdbcInitFile.append("upgrade init --connector database --homeDir "+cmsHomeDir+" --webApp ../webapp/webapp.war\n")
    jdbcInitFile.append("upgrade check\n")
}

def help()
{
    println("This script creates a scriptfile for cms-shell to configure a jdbc connections, set upgrade parameters and do a 'upgrade check'")
    println("Usage:")
    println("contextParser.groovy <context.xml> <cms.home dir> <em-shell script filename>")
    System.exit(2)
}

if ( args.length == 3 && new File(args[0]).isFile() && new File(args[1]).isDirectory() )
{
    contextParser(args[0], args[1], args[2])
} else {
    help()
}

