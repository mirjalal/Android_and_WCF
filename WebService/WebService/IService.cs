using System.ServiceModel;
using System.ServiceModel.Web;

namespace WebService
{
    [ServiceContract]
    public interface IService
    {
        [OperationContract]
        [WebGet(UriTemplate = "GetImage", RequestFormat = WebMessageFormat.Xml, ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Bare)]
        System.IO.Stream GetImage ();

        [OperationContract]
        [WebInvoke(Method = "GET", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json, UriTemplate = "/register/{username}/{password}/{name}/{surname}/{graduated_from}/{graduated_in}/{born_place}/{birthday}")]
        //[WebInvoke(Method = "GET", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json, UriTemplate = "register?username={username}&password={password}&name={name}&surname={surname}")]
        string register (string username, string password, string name, string surname, string graduated_from, string graduated_in, string born_place, string birthday, string profile_pic);

        
    }
}
