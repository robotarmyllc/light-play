/*
 * FTDI Interface for D2XX drivers.
 */
package jftdi;

import com.sun.jna.Library;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.PointerByReference;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author mark
 */
public interface ID2XX extends Library {

    public class FtStatus extends NativeLong {
    };

    public static class FtDeviceListInfoNode extends Structure {

        public WinDef.ULONG Flags;
        public WinDef.ULONG Type;
        public WinDef.ULONG ID;
        public WinDef.DWORD LocId;
//        public String SerialNumber = "AAAABBBBCCCCDDDD";
        public PointerByReference SerialNumber = new PointerByReference();
//        public String Description = "AAAABBBBCCCCDDDDAAAABBBBCCCCDDDDAAAABBBBCCCCDDDDAAAABBBBCCCCDDDD";
        public PointerByReference Description = new PointerByReference();
        public Pointer ftHandle;

        public static class ByReference extends FtDeviceListInfoNode implements Structure.ByReference {
        }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList(new String[]{"Flags", "Type", "ID", "LocId", "SerialNumber", "Description", "ftHandle"});
        }
    }

    public static class PFtDeviceListInfoNode extends Structure {

        public PFtDeviceListInfoNode.ByReference pList;
        
        public static class ByReference extends PFtDeviceListInfoNode implements Structure.ByReference {}

        public PFtDeviceListInfoNode() {}

        @Override
        public FtDeviceListInfoNode.ByReference[] toArray(int size) {
            return pList.toArray(size);
        }
        
        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList(new String[]{"pList"});
        }
}

    // FT_STATUS FT_CreateDeviceInfoList (LPDWORD lpdwNumDevs)
    public FtStatus FT_CreateDeviceInfoList(PointerByReference numDevs);

    // FT_STATUS FT_GetDeviceInfoList (FT_DEVICE_LIST_INFO_NODE *pDest, LPDWORD lpdwNumDevs)
    public FtStatus FT_GetDeviceInfoList(FtDeviceListInfoNode.ByReference infoNode, PointerByReference numDevs);

    /**
     * FTD2XX_API FT_STATUS WINAPI FT_GetDeviceInfoDetail( DWORD dwIndex,
     * LPDWORD lpdwFlags, LPDWORD lpdwType, LPDWORD lpdwID, LPDWORD lpdwLocId,
     * LPVOID lpSerialNumber, LPVOID lpDescription, FT_HANDLE *pftHandle	)
     */
    public FtStatus FT_GetDeviceInfoDetail(int index,
            Pointer flags, Pointer type, Pointer id, Pointer locId,
            Pointer serialNumber, Pointer Description,
            Pointer ftHandle);

    // FTD2XX_API FT_STATUS WINAPI FT_OpenEx(  PVOID pArg1, DWORD Flags, FT_HANDLE *pHandle )
    public FtStatus FT_OpenEx(Pointer arg1, int flags, Pointer ftHandle);

    // FTD2XX_API FT_STATUS WINAPI FT_Close(FT_HANDLE ftHandle )
    public FtStatus FT_Close(Pointer ftHandle);

    // FTD2XX_API FT_STATUS WINAPI FT_SetBreakOn( FT_HANDLE ftHandle )
    public FtStatus FT_SetBreakOn(Pointer ftHandle);

    // FTD2XX_API FT_STATUS WINAPI FT_SetBreakOff( FT_HANDLE ftHandle )
    public FtStatus FT_SetBreakOff(Pointer ftHandle);

    // FTD2XX_API FT_STATUS WINAPI FT_SetBaudRate(FT_HANDLE ftHandle,ULONG BaudRate)
    public FtStatus FT_SetBaudRate(Pointer ftHandle, int BaudRate);

    /**
     * FTD2XX_API FT_STATUS WINAPI FT_SetDataCharacteristics( FT_HANDLE
     * ftHandle, UCHAR WordLength, UCHAR StopBits, UCHAR Parity )
     */
    public FtStatus FT_SetDataCharacteristics(Pointer ftHandle, byte wordLength, byte stopBits, byte parity);

    /**
     * FTD2XX_APIFT_STATUS WINAPI FT_SetFlowControl( FT_HANDLE ftHandle, USHORT
     * FlowControl, UCHAR XonChar, UCHAR XoffChar )
     */
    public FtStatus FT_SetFlowControl(Pointer ftHandle, short flowControl, byte xOnChar, byte xOffChar);
}
