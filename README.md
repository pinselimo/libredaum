# Liberate your Daum

The library ```libredaum``` provides the full stack of the communication protocol for Daum fitness equipment.
The long term goal is to provide interfaces for multiple languages.

## Usage

Depending on the Daum fitness device control is possible via various hardware interfaces. The higher-end devices support TCP/IP while lower end ones often only offer RS-232 serial communication via USB to Serial dongles.
The SP-KOMM software protocol however remains the same.

This library is thus split into two parts: Communications and protocol support.

#### TCP / IP

You can find the IP address of your device under ```Information / Networks```. This IP must be handed to the ```connect``` method of the ```ConnectionProvider``` instance.

#### RS-232

Not yet implemented.

For further details on usage documentation will be available in the future.

### Packages

The protocol offers three types of packages. Two are to be sent, while the third is for responses of the device only.

+ A **Request** can be sent to query data from the device.
+ A **Command** is used to alter device properties.

In both cases the device returns a response with the (new/requested) properties.

## License

This library's source code is distributed open source under the terms of the MIT license. For further details refer to the ```LICENSE``` file.

> © 2020, Simon Plakolb

