package me.ledovec;

import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster;
import com.ghgande.j2mod.modbus.util.SerialParameters;

public class Main {

    protected static ModbusTCPMaster master;

    private static final String SERIAL_1 = "ttyUSB0";
    private static final String SERIAL_2 = "ttyUSB1";

    private static final String REMOTE_ADDRESS = "192.168.200.31";

    public static void main(String[] args) {
        enableLocal();
        enableRemote();
    }

    private static void enableLocal() {
        SerialParameters serialParameters1 = new SerialParameters();
        serialParameters1.setPortName(SERIAL_1);
        serialParameters1.setBaudRate(9600);
        ModbusSerialMaster modbusSerialMaster = new ModbusSerialMaster(serialParameters1);

        SerialParameters serialParameters2 = new SerialParameters();
        serialParameters2.setPortName(SERIAL_2);
        serialParameters2.setBaudRate(9600);
        ModbusSerialMaster modbusSerialMaster1 = new ModbusSerialMaster(serialParameters2);
        try {
            boolean isOn = true;
            modbusSerialMaster.connect();
            modbusSerialMaster1.connect();
            while (true) {
                for (int i = 1; i < 11; i++) {
                    modbusSerialMaster.writeCoil(1, i, isOn);
                    modbusSerialMaster1.writeCoil(1, 11 - i, isOn);
                }
                isOn = !isOn;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void enableRemote() {
        boolean turnOn = false;
        while (true) {
            try {
                master = new ModbusTCPMaster(REMOTE_ADDRESS);
                if (!master.isConnected()) {
                    master.connect();
                }
                for (int j = 0; j < 37; j++) {
                    master.writeCoil(1, j, turnOn);
                }
                turnOn = !turnOn;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (master != null) {
                    master.disconnect();
                }
            }
        }
    }

}