using System;
using Amqp;

namespace dotnet_client
{
    class program
    {
        static void Main(string[] args)
        {
            string broker  = args.Length >= 1 ? args[0] : "amqp://localhost:8583";
            string address = args.Length >= 2 ? args[1] : "address.helloworld";

            Address brokerAddr = new Address(broker);
            Connection connection = new Connection(brokerAddr);
            Session session = new Session(connection);

            SenderLink sender = new SenderLink(session, "dotnetclient-sender", address);
            ReceiverLink receiver = new ReceiverLink(session, "dotnetclient-receiver", address);

            Message helloOut = new Message("This is an AMQP message sent by a Dot Net client!");
            sender.Send(helloOut);

            Message helloIn = receiver.Receive();
            receiver.Accept(helloIn);

            Console.WriteLine(helloIn.Body.ToString());

            receiver.Close();
            sender.Close();
            session.Close();
            connection.Close();
        }
    }
}
