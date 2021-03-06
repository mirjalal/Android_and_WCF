﻿using System.Collections.Generic;
using System.IO;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;

namespace WebService
{
    [ServiceContract]
    public interface IService
    {
        [OperationContract]
        [WebGet(UriTemplate = "getimage", RequestFormat = WebMessageFormat.Xml, ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Bare)]
        Stream GetImage();


        [OperationContract]
        [WebInvoke(Method = "POST", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json, UriTemplate = "/register/{username}/{password}/{name}/{surname}/{graduated_from}/{graduated_in}/{born_place}/{birthday}/{profile_pic}")]
        //[WebInvoke(Method = "GET", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json, UriTemplate = "register?username={username}&password={password}&name={name}&surname={surname}")]
        string register(string username, string password, string name, string surname, string graduated_from, string graduated_in, string born_place, string birthday, Stream profile_pic);


        // http://stackoverflow.com/a/2089974/4057688
        [OperationContract]
        [WebInvoke(Method = "GET", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json, UriTemplate = "login/{username}/{password}", BodyStyle = WebMessageBodyStyle.WrappedResponse)]
        List<UserDetails> login(string username, string password);
    }

    [DataContract]
    public class UserDetails
    {
        [DataMember]
        public string name
        {
            get;
            set;
        }

        [DataMember]
        public string surname
        {
            get;
            set;
        }

        [DataMember]
        public string graduated_from
        {
            get;
            set;
        }

        [DataMember]
        public string graduated_in
        {
            get;
            set;
        }

        [DataMember]
        public string born_place
        {
            get;
            set;
        }

        [DataMember]
        public string birthday
        {
            get;
            set;
        }


        public UserDetails(string name, string surname, string graduated_from, string graduated_in, string born_place, string birthday)
        {
            this.name = name;
            this.surname = surname;
            this.graduated_from = graduated_from;
            this.graduated_in = graduated_in;
            this.born_place = born_place;
            this.birthday = birthday;
        }
    }
}
