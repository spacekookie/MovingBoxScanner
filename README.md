# About this Application

The Box Inventory Scanner (also Box Scan Pro) is an application that accesses your local QR code scanner, reads a QR code and then displays the stored information in an organised fashion.

The application was designed to work in cooperation with our web applet over at www.2rsoftworks.de/moveit

Alternatively you can just create your own xml texts in this manner:

```
<box>
	<name>Name</name>
	<location>Location</location>
	<contents>
		<item>Item 1</item>
		<item>Item 2</item>
		<item>Item 3</item>
	</contents>
</box>
```

ONLY STORE ONE BOX PER QR CODE. Additional boxes will be ignored!
