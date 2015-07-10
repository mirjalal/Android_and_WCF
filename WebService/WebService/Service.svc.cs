using MySql.Data.MySqlClient;
using System;
using System.Data;
using System.IO;
using System.Runtime.Serialization.Json;
using System.Collections.Generic;
using System.ServiceModel.Web;
using System.Text;

namespace WebService
{
    internal class Service : IService
    {
        private static MySqlConnection connection = new MySqlConnection("server=45.35.4.29;uid=root;password=connection;database=db_webservice");

        // http://stackoverflow.com/q/472906
        // http://stackoverflow.com/q/1003275
        // http://stackoverflow.com/q/10513976

        //public class Convert
        //{
        //    public static byte[] ConvertToBytes(string str)
        //    {
        //        byte[] bytes = new byte[str.Length * sizeof(char)];
        //        Buffer.BlockCopy(str.ToCharArray(), 0, bytes, 0, bytes.Length);

        //        return bytes;
        //    }

        //    public static string ConvertToString(byte[] bytes)
        //    {
        //        char[] chars = new char[bytes.Length / sizeof(char)];
        //        Buffer.BlockCopy(bytes, 0, chars, 0, bytes.Length);

        //        return new string(chars);
        //    }
        //}


        public Stream GetImage ()
        {
            FileStream fs = File.OpenRead(@"D:\a.jpg");
            WebOperationContext.Current.OutgoingResponse.ContentType = "image/jpeg";
            return fs;
        }


        // https://youtu.be/iqrY9IaUY24
        // https://youtu.be/AMH4plUP0uo
        public string register (string username, string password, string name, string surname, string graduated_from, string graduated_in, string born_place, string birthday/*, string profile_pic*/)
        {
            bool result = false;

            string query_insert = @"INSERT INTO users(username, password, name, surname, graduated_from, graduated_in, born_place, birthday) VALUES( ' " + username + " ', ' " + password + " ', ' " + name + " ', ' " + surname + " ', ' " + graduated_from + " ', ' " + graduated_in + " ', ' " + born_place + " ', ' " + birthday + " '); "; //, '" + profile_pic + " ' );";

            if (connection.State == ConnectionState.Closed)
                connection.Open();

            using (MySqlCommand insert = new MySqlCommand(query_insert, connection))
            {
                insert.ExecuteNonQuery();
                result = true;
            }

            connection.Close();

            if (result)
                return "success";
            else
                return "fail";
        }


        // https://goo.gl/PXc6Z4
        // http://stackoverflow.com/q/29414960
        // http://stackoverflow.com/q/8270464
        // http://stackoverflow.com/q/2108297
        public List<UserDetails> login (string username, string password)
        {
            int _id = 0;
            List<UserDetails> details = new List<UserDetails>();

            if (connection.State == ConnectionState.Closed)
                connection.Open();

            using (MySqlCommand search = new MySqlCommand("SELECT _id FROM users WHERE username = ' " + username + " ' AND password = ' " + password + " ' ", connection))
            {
                _id = Convert.ToInt32(search.ExecuteScalar());
            }

            connection.Close();
            connection.Open();

            if (_id != 0)
            {
                using (MySqlCommand data = new MySqlCommand("SELECT name, surname, graduated_from, graduated_in, born_place, birthday FROM users WHERE _id = " + _id + ";", connection))
                {
                    MySqlDataReader get_data = data.ExecuteReader();

                    while (get_data.Read())
                    {
                        details.Add(new UserDetails(get_data.GetString(0), get_data.GetString(1), get_data.GetString(2), get_data.GetString(3), get_data.GetString(4), get_data.GetString(5)));
                    }

                    get_data.Close();

                    // Serialize the results as JSON
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(details.GetType());
                    MemoryStream memoryStream = new MemoryStream();
                    serializer.WriteObject(memoryStream, details);

                    // Return the results serialized as JSON
                    //return Encoding.UTF8.GetString(memoryStream.ToArray());

                    return details;
                }
            }
            else
                return details;
        }
    }
}
