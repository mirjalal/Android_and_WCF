using System.ServiceModel;
using System.ServiceModel.Web;

namespace WebService
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IService" in both code and config file together.
    [ServiceContract]
    [XmlSerializerFormat]
    public interface IService
    {
        [OperationContract]
        [WebInvoke(Method = "GET", RequestFormat = WebMessageFormat.Xml, ResponseFormat = WebMessageFormat.Xml, UriTemplate = "login?istiad={istiad}", BodyStyle = WebMessageBodyStyle.Bare)]
        string Login(string istiad/*, string sifre*/);
    }
}
