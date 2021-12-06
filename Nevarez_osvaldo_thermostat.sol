pragma solidity >=0.7.0 <0.9.0; //Change this to current version

contract MyThermostat
{
    //Set of States
    enum ThermState { CREATED, INUSE}
    
    //List of properties
    ThermState public State;
    address public Installer;
    address public User;
    int public TargetTemp;
    enum ThermMode {OFF, COOL, HEAT}
    ThermMode public  Mode;
    
// address of installer and user is predefined in the constructor
    constructor(address installerGuyAddr, address userAddr) public{
        Installer = installerGuyAddr;
        User = userAddr;
        TargetTemp = 70;
    }

// logic to start the thermostat (can be executed by installer only)
    function StartThermostat() public{
        if (Installer != msg.sender || State != ThermState.CREATED){
            revert();
        }
        State = ThermState.INUSE;
    }

//logic to set target temperature (can be executed by user only)
    function SetTargetTemperature(int targetTemperature) public{
        if (User != msg.sender || State != ThermState.INUSE)
        {
            revert();
        }
        TargetTemp = targetTemperature;
    }

//logic to set mode of the thermostat (can be executed by user only)
    function SetMode(ThermMode mode) public {
        if (User != msg.sender || State != ThermState.INUSE)
        {
            revert();
        }
        Mode = mode;
    }
}
