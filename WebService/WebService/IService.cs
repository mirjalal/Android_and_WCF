using System.ServiceModel;
using System.ServiceModel.Web;

namespace WebService
{
    [ServiceContract]
    public interface IService
    {
        [OperationContract]
        [WebInvoke(Method = "GET", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json, UriTemplate = "login?istiad={istiad}&sifre={sifre}")]
        string Login(string istiad, string sifre);

        [WebGet(UriTemplate = "GetImage", RequestFormat = WebMessageFormat.Xml, ResponseFormat = WebMessageFormat.Xml, BodyStyle = WebMessageBodyStyle.Bare)]
        System.IO.Stream GetImage ();
    }
}
