# plantation
OpenSource way to build realtime data server

## Getting Start

pre request
  
  * RethinkDB
  * Java 1.8

1. git clone 
    git clone https://github.com/e-green/plantation.git
2. cd plantation
3. Change Database table name and host in options.properties
    ```
        DB.NAME=grizzly_china_38456 # DB NAME
        DB.HOST=localhost # DB Host
        DB.PORT=28015 # DB Host Port
    ```
    
4. Start Plantation
    ```
        gradle server:run
        
    ```
5. Usefull Links
    - Setup              - http://localhost:8090/api/setup <POST method>
    - Subscribe          - http://localhost:8090/api/push/<ENTITY>
    - Web Socket Listner - ws://localhost:8091/bind/<ENTITY>


## licence 
Quick Summary
A short, permissive software license. Basically, you can do whatever you want as long as you include the original copyright and license in their respective sources.


Can
 * Modify 
 * Distribute 
 * Sublicense 
 * Private Use 

Cannot
 * Hold Liable 
 * Commercial Use 

Must
 * Include Copyright 
 * Include License 
