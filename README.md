# rpi-rest
This is a middleware implementation that allows tansparent access to Raspberry PI's GPIO pins via direct calls to [PI4J](http://pi4j.com/) or via remote HTTP REST calls.

This is not just a REST API service to control GPIO, but a whole abstraction layer that allows you to send commands to GPIOs regardless of whether your application is running locally in the Raspberry PI or remotely in your development machine.

To better illustrate this abstraction layer consider the following code snippet whose goal is to make two leds connected to GPIO's `1` and `0` turn on and off...
```java
RpiController getController()
{
    RpiController controller = null;
    
    if(cmdArgs.runLocally())
    {
        controller = new RpiController();
    }
    else
    {
        RestConfig config = new RestConfig();
        config.setServer(cmdArgs.getServer());
        config.setPort(cmdArgs.setPort());
        config.setApiKey(cmdArgs.getApiKey();
        
        controller = new RpiController(config);
    }
    
    return controller;
}

void makeLedsPulse()
{
    RpiController controller = getController();
    OutputPin gpio_0 = controller.getOutGpio(0);
    OutputPin gpio_1 = controller.getOutGpio(WiringPi.GPIO_1);
    
    for(i = 0; i < 10; i++)
    {
        gpio_0.up();
        gpio_1.down();
        Sleep(100);
        gpio_0.up();
        gpio_1.down();
        Sleep(100);
    }
}
```

Lets explain what's going on:
* The controller object is created based on flags passed via command line. In this scenario we could either run with a `local` controller, or with a `REST` based controler.
* Note how the business logic remains unaffected when we change the controller's underlying behavior.
* There are several overloads to address GPIO pins: enum, constant, physical pin number.
* This project, like PI4J, uses the [WiringPi](http://wiringpi.com/) pin layout. WirinPi is an abstract pin numbering scheme that helps insulate software from hardware changes.

## Project status
*"Sounds good, doesn't work" - Trump*

**Last update**: 06/18/2017 `Work In Progress`
### What is done
* Underlying architecture is defined.
* Project scheleton created with Maven.
* Added class hierarchies and helper classes.
* Added ULT for WiringPi scheme to ensure pin mapping was done correctly.
* Milestone 1 **[complete]**: implement local controller and local pin controllers. Expose Pi4j functionality through this library's middleware.

### Next steps
* Milestone 2: Write ULT and make sure local controller works when running in the RaspberryPi.
* Milestone 3: Define REST API based on controller interface.
* Milestone 4: Implement REST API and do basic testing.
* Milestone 5: Implement REST based pin controller and run ULT created in Milestone 2. ULT must behave the same when running localy or remotely.
* Milestone 6: Create external app to showcase library usage.
