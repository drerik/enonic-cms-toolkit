#!/usr/bin/env groovy

/**
 * Tool to match ServerNames in vhost configurations against a spesific ipadress.
 * Prints out "<configfile> :  <domainname> -> <ipadress>"
 *
 * */

if ( ! args.length == 2)
{
    println("Usage: matchIpFromVhosts.groovy <httpd vhost config dir> <ip address>")
    System.exit(2)
}


def vhostDir = new File(args[0]) // apache vhost directory
def serverIpAddr = args[1] //"ip address to check against"

vhostDir.eachFile() {
    def vhostFile = new File(vhostDir,it.name)

    //println("Parsing "+vhostFile)

    vhostFile.readLines().each {
        def line = it.stripIndent()
        if (!line.startsWith("#") && line.startsWith("ServerName"))
        {
            def dnsName = line.split(" ")[1]

            def ipaddr = ""
            try {
                ipaddr = InetAddress.getByName(dnsName).hostAddress
            } catch ( UnknownHostException) {
                ipaddr = ""
            }
            if ( ipaddr.contains(serverIpAddr))
            {
                println(vhostFile.name +" :  " + dnsName + " -> " + ipaddr)
            }

        }

    }

}
