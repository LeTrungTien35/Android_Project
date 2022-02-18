using System;
using System.Collections.Generic;
using System.IO;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace webTestUR
{
    public partial class nhan_json : System.Web.UI.Page
    {
        class PhanHoi
        {
            public bool ok;
            public string msg;
        }
        protected void Page_Load(object sender, EventArgs e)
        {
            PhanHoi P = new PhanHoi();
            try
            {
                //get request stream => lấy chuỗi json gửi lên
                string json;
                using (var reader = new StreamReader(Request.InputStream))
                {
                    json = reader.ReadToEnd();
                    //lưu csdl
                    bool ok=LuuCSDL.SaveDB(json);
                    P.msg = ok?"Đã lưu SMS vào CSDL thành công":"Có gì đó sai sai khi lưu và csdl";
                    P.ok = ok;
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