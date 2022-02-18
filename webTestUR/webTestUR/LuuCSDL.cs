using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Web;

using System.Data;
using System.Data.SqlClient;

namespace webTestUR
{
    public class LuuCSDL
    {
        class SMS
        {
            public string unique, num, msg, time;
        }
        static public bool SaveDB(string json)
        {
            //chuyển string json -> object JSON

            SMS m = JsonConvert.DeserializeObject<SMS>(json);

            //kết nối csdl
            string cn_string = "Server=localhost;Database=SMS_BACKUP;User Id=SMS_BACKUP;Password=123456;";
            SqlConnection cn = new SqlConnection(cn_string);
            cn.Open();

            //tạo sql để luu
            string sql = "INSERT INTO SMS([unique],NUM,MSG,TIME)VALUES(@unique,@NUM,@MSG,@TIME);";
            //thực thi nó
            SqlCommand cm = new SqlCommand(sql, cn);
            cm.Parameters.Add("@unique", SqlDbType.VarChar, 16).Value = m.unique;
            cm.Parameters.Add("@NUM", SqlDbType.VarChar, 20).Value = m.num;
            cm.Parameters.Add("@MSG", SqlDbType.NVarChar, 500).Value = m.msg;


            DateTime myDate = DateTime.ParseExact(m.time, "dd/MM/yyyy HH:mm:ss",
                                       System.Globalization.CultureInfo.InvariantCulture);

            cm.Parameters.Add("@TIME", SqlDbType.DateTime).Value = myDate;
            return 1==cm.ExecuteNonQuery();
        }
    }
}