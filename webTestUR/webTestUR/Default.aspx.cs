using System;
using System.Collections.Generic;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace webTestUR
{
    public partial class _Default : System.Web.UI.Page
    {
        string get_session_var(string var_name)
        {
            try
            {
                string value = Session[var_name].ToString();
                return value;
            }
            catch(Exception ex)
            {
                return ex.Message;
            }
        }
        protected void Button1_Click(object sender, EventArgs e)
        {
            string s = TextBox1.Text;
            ketqua.InnerHtml = get_session_var(s);
        }
    }
}