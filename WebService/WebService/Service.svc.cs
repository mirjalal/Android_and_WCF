using MySql.Data.MySqlClient;
using System;
using System.Data;
using System.Drawing;
using System.IO;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Json;
using System.ServiceModel;
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


        public class DataAccess
        {
            public string checkLogin (string istiad, string sifre)
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


        public string register (string username, string password, string name, string surname, string graduated_from, string graduated_in, string born_place, string birthday)
        {
            bool result = false;

            string query_insert = @"INSERT INTO users(username, password, name, surname) VALUES( ' " + username + " ', ' " + password + " ', ' " + name + " ', ' " + surname + "' );";

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
