package com.devgd.melonclone.utils.network.services;

import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.utils.store.MusicSample;

import org.watermelon.framework.utils.network.httpconnection.HttpConnector;
import org.watermelon.framework.utils.network.httpconnection.HttpSender;
import org.watermelon.framework.utils.network.services.HttpConnectionServiceImpl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

public class SampleService extends HttpConnectionServiceImpl {

    public HttpSender.Call<Music> getMusic() {
        getHttpSender().setMethod(HttpConnector.HttpMethod.GET);
        return callback ->
                getHttpSender()
                    .call(connection -> {
                        try {
                            connection.setDoOutput(false);
                            // 응답
                            InputStream is;

                            // 전송 응답 받음
                            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                                is = connection.getErrorStream();
                            } else {
                                is = connection.getInputStream();
                            }

                            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                            String line = null;
                            StringBuilder sb = new StringBuilder();

                            while ((line = br.readLine()) != null) {
                                sb.append(line);
                            }

                            // 닫기
                            br.close();
                            is.close();


                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // TODO Callback
                        return MusicSample.getSample();
                    }, callback);
    }

    public HttpSender.Call<Music> addMusic(Music music) {
        getHttpSender().setMethod(HttpConnector.HttpMethod.POST);
        return callback ->
                getHttpSender()
                    .call(connection -> {

                        try {
                            // 데이터
                            // 전송
                            OutputStream os = connection.getOutputStream();

                            // GZIP Compressing
                            BufferedOutputStream bos =
                                    new BufferedOutputStream(os);

                            // Send Dump Data
                            InputStream inputStream = new ByteArrayInputStream(music.toString().getBytes());
                            BufferedInputStream bis = new BufferedInputStream(inputStream);
                            byte[] buffer = new byte[1024]; // Adjust if you want
                            int bytesRead;

                            while ((bytesRead = bis.read(buffer)) != -1) {
                                os.write(buffer, 0, bytesRead);
                            }


                            bos.flush();
                            bis.close();
                            bos.close();
                            os.close();

                            // 응답
                            InputStream is;

                            // 전송 응답 받음
                            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                                is = connection.getErrorStream();
                            } else {
                                is = connection.getInputStream();
                            }

                            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                            String line = null;
                            StringBuilder sb = new StringBuilder();

                            while ((line = br.readLine()) != null) {
                                sb.append(line);
                            }

                            // 닫기
                            br.close();
                            is.close();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // TODO Callback
                        return MusicSample.getSample();
                    }, callback);
    }
}
