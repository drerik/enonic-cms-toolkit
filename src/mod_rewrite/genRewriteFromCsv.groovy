#!/usr/bin/env groovy
/**
 * Script to generate rewrite rules from a csv file seperated by ;
 * By Erik Sunde
 */


def inFile = new File("infile.csv")
def outFile = new File("outfile.txt")
def outText = "### Added from "+ inFile.name+ " @ "+ new Date()+ "  ###\n"


inFile.readLines().each {
    def from = getPathFromUrl(it.split(";")[0])
    def to = it.split(";")[1]


    if (isPageIdLink(from))
    {
        def urlVariables = from.split("\\?")[1]
        outText = outText + "\nRewriteCond %{QUERY_STRING} "+urlVariables + "\n"
        outText = outText + "RewriteRule ^/page         "+ to +"? [L,R=301]\n"
    } else {
        outText = outText + "RewriteRule ^"+ from + "\$         "+ to +"? [L,R=301]\n"
    }


}

outFile.write(outText)
println("Rules saved in "+outFile.absoluteFile)


def getPathFromUrl(def url)
{
    //removing dns name
    def pathList = url.split("/").drop(1).drop(2)
    def path
    pathList.each { path = "/" + it}
    return path
}

def isPageIdLink(def path)
{
    if (path.contains("/page?id="))
    {
        return true
    }
}