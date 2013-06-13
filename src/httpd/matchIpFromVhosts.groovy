#!/usr/bin/env groovy

/**
 * Tool to match ServerNames in vhost configurations against a spesific ipadress.
 * Prints out "<configfile> :  <domainname> -> <ipadress>"
 *
 * */


def vhostDir = new File("/etc/apache2/sites-enabled") // apache vhost directory
def serverIpAddr = "123.123.123.123" //"ip address to check against"

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
