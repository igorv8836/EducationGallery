package com.example.educationgallery.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;


public class Parser
{

    private final String encodedGroup;
    private final HttpsURLConnection con;
    private JSONObject jsonObject;

    public Parser(String groupName) throws MalformedURLException, ConnectException, JsonException
    {
        encodedGroup = URLEncoder.encode(groupName);
        con = connectionSecured();
        jsonObject = jsonCorrect();
    }

    public void fillDB(AppDatabase db)
    {
        try {
            JSONArray schedule = jsonObject.getJSONArray("schedule");

            for(int j=0;j<schedule.length();j++)
            {
                JSONArray even = schedule.getJSONObject(j).getJSONArray("even");
                JSONArray odd = schedule.getJSONObject(j).getJSONArray("odd");
                WeekDay WeekDay = dayDefining(schedule.getJSONObject(j).getString("day"));

                Day day = new Converters().fillDay( WeekDay, arrayListLesson(odd), arrayListLesson(even));

                db.dayDao().insertAll(day);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpsURLConnection connectionSecured() throws MalformedURLException, ConnectException
    {
        String mainLink = "https:mirea.xyz/api/v1.3/groups/certain?name=";
        String link = mainLink +encodedGroup;
        HttpsURLConnection con = null;
        try {
            URL u = new URL(link);
            con = (HttpsURLConnection) u.openConnection();
            con.connect();

            return con;

        } catch (MalformedURLException e) {
            throw new MalformedURLException();
        } catch (IOException e) {
            throw new ConnectException();
        }

    }

    private JSONObject jsonCorrect() throws JsonException
    {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            JSONArray jsonArray = new JSONArray(sb.toString());
            jsonObject = jsonArray.getJSONObject(0);
            if(jsonObject == null)
            {
                throw new JsonException("Empty json");
            }
        }catch (IOException e)
        {
            throw new JsonException("Empty json");
        } catch (JSONException e)
        {
            throw new RuntimeException(e);
        }finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return jsonObject;
    }

    private WeekDay dayDefining(String text)
    {
        switch (text)
        {
            case "понедельник":
                return WeekDay.MONDAY;
            case "вторник":
                return WeekDay.TUESDAY;
            case "среда":
                return WeekDay.WEDNESDAY;
            case "четверг":
                return WeekDay.THURSDAY;
            case "пятница":
                return WeekDay.FRIDAY;
            case "суббота":
                return WeekDay.SATURDAY;
            default:
                return null;
        }
    }

    private ArrayList<Lesson> arrayListLesson(JSONArray array) throws JSONException
    {
        ArrayList<Lesson> subjects = new ArrayList<>();
        for (int i = 0; i < array.length(); i++)
        {
            JSONArray lesson = array.getJSONArray(i);
            if (!lesson.isNull(0))
                subjects.add(new Lesson(lesson.getJSONObject(0).get("type").toString(),lesson.getJSONObject(0).get("name").toString()));
            else
                subjects.add(null);
        }
        return subjects;
    }

}
