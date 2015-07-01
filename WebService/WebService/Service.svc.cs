using MySql.Data.MySqlClient;
using System;
using System.Data;
using System.Drawing;
using System.IO;
using System.ServiceModel;
using System.ServiceModel.Web;

namespace WebService
{
    public class Service : IService
    {
        public string Login(string istiad, string sifre)
        {
            DataAccess dataAccess = new DataAccess();

            return dataAccess.checkLogin(istiad, sifre);
        }


        public class DataAccess
        {
            MySqlConnection connection;

            public DataAccess()
            {
                connection = new MySqlConnection("server=45.35.4.29;uid=root;password=connection;database=erp");
            }


            public string checkLogin(string istiad, string sifre)
            {
                if (connection.State == ConnectionState.Closed)
                    connection.Open();

                MySqlCommand command = new MySqlCommand(" SELECT COUNT(*) FROM users WHERE username='" + istiad + "' ", connection);
                int user_count = Convert.ToInt32(command.ExecuteScalar());

                if (user_count == 1)
                    return "1";
                else
                    return "0";
            }
        }


        public Stream GetImage ()
        {
            FileStream fs = File.OpenRead(@"D:\a.jpg");
            WebOperationContext.Current.OutgoingResponse.ContentType = "image/jpeg";
            return fs;
        }
    }
}
