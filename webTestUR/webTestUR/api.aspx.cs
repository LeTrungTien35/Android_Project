using System;
using System.Collections.Generic;
using System.IO;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace webTestUR
{
    public partial class api : System.Web.UI.Page
    {
        class PhanHoi
        {
            public bool ok;
            public string msg, msg2;
        }
        protected void Page_Load(object sender, EventArgs e)
        {
            PhanHoi P = new PhanHoi();
            try
            {
                //set value for session[name]
                string name = Request["name"];
                string value = Request["value"];
                if (name != null && name != "")
                {
                    P.ok = true;
                    Session[name] = value;  //set here
                    //Response.Write(string.Format("Đã lưu Session[{0}]={1}", name, value));
                    P.msg = string.Format("Đã lưu Session[{0}]={1}", name, value);
                    if (name == "json")
                    {
                        //get request stream => lấy chuỗi json gửi lên
                        string json;
                        using (var reader = new StreamReader(Request.InputStream))
                        {
                            json = reader.ReadToEnd();
                            Session["json_string"] = json;  //set here
                            //Response.Write(string.Format("\nĐã lưu Session[json_string]={1}", name, json));
                            P.msg2 = string.Format("\nĐã lưu Session[json_string]={1}", name, json);
                        }
                    }
                }
                else
                {
                    P.ok = false;
                }
            }
            catch (Exception ex)
            {
                P.ok = false;
                P.msg = ex.Message;
            }
            finally
            {
                //chuyển đối tượng P -> chuỗi json
                string json_msg = Newtonsoft.Json.JsonConvert.SerializeObject(P);
                Response.Write(json_msg);
            }
        }
    }
}