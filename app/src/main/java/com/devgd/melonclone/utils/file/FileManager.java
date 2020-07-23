package com.devgd.melonclone.utils.file;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;

public class FileManager {
    private static final String TAG = "File Manager";

    private static final String LOCAL_ROOT_PATH = "/melon";
    private static final String EXTERNAL_ROOT_PATH = "/melon";
    private static final String CUSTOM_ROOT_PATH = "custom";

    public static String getLocalPath(Context context) {

        String localPath = context.getFilesDir().getAbsolutePath() + LOCAL_ROOT_PATH;

        if (Environment.getDataDirectory() != null) {
            boolean result = new File(localPath).mkdir();
//            Log.d("MKDIR RESULT", result+"");
        }

        return localPath;
    }

    public static String getExternalPath() {
        String externalPath = Environment.getExternalStorageDirectory().getAbsolutePath() + EXTERNAL_ROOT_PATH;

        if (Environment.getExternalStorageDirectory() != null) {
            boolean result = new File(externalPath).mkdir();
//            Log.d("MKDIR RESULT", result+"");
        }

        return externalPath;
    }

    /**
     * 디렉토리 생성
     * @return dir
     */
    public static File makeDirectory(String dir_path){
        File dir = new File(dir_path);
        if (!dir.exists())
        {
            boolean result = dir.mkdirs();
            Log.i( TAG , "!dir.exists : " + result);
        }else{
            Log.i( TAG , "dir.exists" );
        }

        return dir;
    }

    /**
     * 파일 복사
     * @param file
     * @param save_file
     * @return
     */
    public static boolean copyFile(File file , String save_file){
        boolean result;
        if(file!=null&&file.exists()){
            try {
                FileInputStream fis = new FileInputStream(file);
                FileOutputStream newfos = new FileOutputStream(save_file);
                int readcount=0;
                byte[] buffer = new byte[1024];
                while((readcount = fis.read(buffer,0,1024))!= -1){
                    newfos.write(buffer,0,readcount);
                }
                newfos.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            result = true;
        }else{
            result = false;
        }
        return result;
    }

    /**
     * 파일 삭제
     * @param file
     * @return
     */
    public static boolean removeFile(File file){
        boolean result;
        if(file!=null&&file.exists()){
            file.delete();
            result = true;
        }else{
            result = false;
        }
        return result;
    }

    /**
     * 파일 생성
     * @param dir
     * @return file
     */
    public static File makeFile(File dir , String file_path){
        File file = null;
        boolean isSuccess = false;
        if(dir.isDirectory()){
            file = new File(file_path);
            if(file!=null&&!file.exists()){
                Log.i( TAG , "!file.exists" );
                try {
                    isSuccess = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally{
                    Log.i(TAG, "파일생성 여부 = " + isSuccess);
                }
            }else{
                Log.i( TAG , "file.exists" );
            }
        }
        return file;
    }

    /**
     * 파일에 내용 쓰기
     * @param file
     * @param file_content
     * @return
     */
    public static boolean writeFile(File file , byte[] file_content){
        boolean result;
        FileOutputStream fos;
        if(file!=null&&file.exists()&&file_content!=null){
            try {
                fos = new FileOutputStream(file);
                try {
                    fos.write(file_content);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            result = true;
        }else{
            result = false;
        }
        return result;
    }

    public static String getXXXX(int i) {
        String xxxx = "";
        if (i < 10) {
            xxxx = "000"+i;
        } else if (i < 100) {
            xxxx = "00"+i;
        } else if (i < 1000) {
            xxxx = "0"+i;
        } else {
            xxxx = String.valueOf(i);
        }

        return xxxx;
    }

    public static String getXX(int i) {
        String xx = "";
        if (i < 10) {
            xx = "0"+i;
        } else {
            xx = String.valueOf(i);
        }

        return xx;
    }

    public static String increaseNumber(int max) {
        String increase = "";
        for (int i=0; i<max; i++) {
            increase += i+1;
        }

        return increase;
    }

    /** 전체 내장 메모리 크기를 가져온다 */
    private static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();

        return totalBlocks * blockSize;
    }

    /** 사용가능한 내장 메모리 크기를 가져온다 */
    private static long getInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();

        return availableBlocks * blockSize;
    }

    /** 전체 외장 메모리 크기를 가져온다 */
    private static long getTotalExternalMemorySize() {
        if (isStorage(true) == true) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long totalBlocks = stat.getBlockCountLong();

            return totalBlocks * blockSize;
        } else {
            return -1;
        }
    }

    /** 사용가능한 외장 메모리 크기를 가져온다 */
    private static long getExternalMemorySize() {
        if (isStorage(true) == true) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();
            return availableBlocks * blockSize;
        } else {
            return -1;
        }
    }

    /** 보기 좋게 MB,KB 단위로 축소시킨다 */
    private static String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
                if (size >= 1024) {
                    suffix = "GB";
                    size /= 1024;
                }
            }
        }
        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }

        if (suffix != null) {
            resultBuffer.append(suffix);
        }

        return resultBuffer.toString();
    }

    /** 외장메모리 sdcard 사용가능한지에 대한 여부 판단 */
    private static boolean isStorage(boolean requireWriteAccess) {
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else if (!requireWriteAccess &&
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static String getUsageInternalStorageSize() {
        DecimalFormat df = new DecimalFormat("#,###");
        double storage = 100.0*(getTotalInternalMemorySize()-getInternalMemorySize())/getTotalInternalMemorySize();
        return df.format(storage)+"%";
    }

    public static String getUsageExternalStorageSize() {
        DecimalFormat df = new DecimalFormat("#,###");
        double storage = 100.0*(getTotalExternalMemorySize()-getExternalMemorySize())/getTotalExternalMemorySize();
        return df.format(storage)+"%";
    }
}
