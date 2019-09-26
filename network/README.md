
1 - OSI model: A conceptual model for networked applications can communicate it uses 7 abstraction layer
    1.1 - protocol: implementation
    1.2 - layers                                                                                                                                                                                                             protocol
            Application(software)                                                                                                                                                                  HTTP, FTP, SMTP, SSH, HTTP, SFTP, POP3, IMAP, FTPS. NTP, BITTOUREN  
            Presentation(software) -  character encoding, compression (UTF, ASCII, compression) bundled to application                                         TLS
            Session(software) - bundled to application                                                                                                                                                      -
            Transport(software) - (application to application e.g. email not go to browser, guarantee in order, intact)                                                   Tcp(guarrantee error if message intact, in order,  prevent traffic, but overhead larger bytes.), udp
            Network - Router (end to end communication)                                                                                                                                              Ipv4, Ipv6, ICMP
            Date Link - conversion of electric signal into bits(network interface card switches), only facilitate two devices connection, not three       Ethernet(1/0 to physical), 802.11(wifi)
            Physical - actual wires, radiowaves, hubs                                                                                                                                                     10Base-7





2 - TCP/IP model 5 levels 
  2.2 - layers                                                                                                                                                                                                         protocol                                                                                                                                                             address                   protocol data unit
      Application(software)                                                                                                                                                                  HTTP, FTP, SMTP, SSH, HTTP, SFTP, POP3, IMAP, FTPS. NTP, BITTOUREN                                                                         -                                message   
      Transport(software) - (application to application e.g. email not go to browser, guarantee in order, intact)                                                   Tcp(guarrantee error if message intact, in order,  prevent traffic, but overhead larger bytes.), udp             Port address    TCP segment / upd datagram gram 
      Network - Router (end to end communication)                                                                                                                                              Ipv4, Ipv6, ICMP                                                                                                                                           IP Address(DHCP)       packet/datagram
      Data Link - conversion of electric signal into bits(network interface card switches), only facilitate two devices connection, not three       Ethernet(1/0 to physical), 802.11(wifi)                                                                                                           MAC Address                  frame
      Physical - actual wires, radiowaves, hubs                                                                                                                                                     10Base-7                                                                                                                                                             -                                    bit
