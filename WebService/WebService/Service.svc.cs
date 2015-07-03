using MySql.Data.MySqlClient;
using System;
using System.Data;
using System.IO;
using System.ServiceModel.Web;

namespace WebService
{
    public class Service : IService
    {
        private static MySqlConnection connection = new MySqlConnection("server=45.35.4.29;uid=root;password=connection;database=db_webservice");

        /*    public string Login (string istiad, string sifre)
            {
                DataAccess dataAccess = new DataAccess();

                return dataAccess.checkLogin(istiad, sifre);
            }*/


        //public class DataAccess
        //{
        //    public string checkLogin (string istiad, string sifre)
        //    {
        //        if (connection.State == ConnectionState.Closed)
        //            connection.Open();

        //        MySqlCommand command = new MySqlCommand(" SELECT COUNT(*) FROM users WHERE username='" + istiad + "' ", connection);
        //        int user_count = Convert.ToInt32(command.ExecuteScalar());

        //        if (user_count == 1)
        //            return "1";
        //        else
        //            return "0";
        //    }
        //}


        // http://stackoverflow.com/q/472906
        // http://stackoverflow.com/q/1003275
        // http://stackoverflow.com/q/10513976
        public class Convert
        {
            public static byte[] ConvertToBytes (string str)
            {
                byte[] bytes = new byte[str.Length * sizeof(char)];
                Buffer.BlockCopy(str.ToCharArray(), 0, bytes, 0, bytes.Length);
                return bytes;
            }

            public static string ConvertToString (byte[] bytes)
            {
                char[] chars = new char[bytes.Length / sizeof(char)];
                Buffer.BlockCopy(bytes, 0, chars, 0, bytes.Length);
                return new string(chars);
            }
        }
        

        public Stream GetImage ()
        {
            FileStream fs = File.OpenRead(@"D:\a.jpg");
            WebOperationContext.Current.OutgoingResponse.ContentType = "image/jpeg";
            return fs;
        }


        // https://youtu.be/iqrY9IaUY24
        // https://youtu.be/AMH4plUP0uo
        public string register (string username, string password, string name, string surname, string graduated_from, string graduated_in, string born_place, string birthday, string profile_pic)
        {
            bool result = false;

            string query_insert = @"INSERT INTO users(username, password, name, surname, graduated_from, graduated_in, born_place, birthday) VALUES( ' " + username + " ', ' " + password + " ', ' " + name + " ', ' " + surname + " ', ' " + graduated_from + " ', ' " + graduated_in + " ', ' " + born_place + " ', ' " + birthday + " ' , '" + profile_pic + " ' );";

            if (connection.State == ConnectionState.Closed)
                connection.Open();

            using (MySqlCommand insert = new MySqlCommand(query_insert, connection))
            {
                insert.ExecuteNonQuery();
                result = true;
            }

            if (result)
                return "success";
            else
                return "fail";
        }
    }
}
