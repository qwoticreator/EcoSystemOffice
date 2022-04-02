
<img src="app/src/main/ic_launcher-playstore.png" align="left"
width="200" hspace="10" vspace="10">

## For becoder-hack

I will show you how to build your own smart home. 
It shows the temperature both inside and outside, if the window is open or closed, 
shows when it rains and make alarm when the PIR sensor senses move. 
I made the application on the android to display all the data.

## How work

I will explain how the application works. It's shows all data from your home.
You can click on settings icon to edit your IP address and turn on or off alarm.
When you turn on alarm, app get data from PIR sensor in service and if it's detected move in your home it's make a notification.
App retrieves the data from the motion sensor every minute.
In IP field you must enter your IP address. You can check it here.

<img src="/images/work.png" width="500px">

## Build Arduino
<img src="/images/arduino.png" width="500px">

## Application
<img src="/images/main"
[<img src="/images/main.png" align="left"
width="400"
hspace="10" vspace="10">](/images/main.png)
[<img src="/images/settings.png" align="left"
width="400"
hspace="10" vspace="10">](/images/settings.png)

## Connection port 

You need to open port on your router. 
Open your router configuration and set arduino ip and open port 80. You can see it on image above.

<img src="/images/server.png" width="500px">

## Start it! 
