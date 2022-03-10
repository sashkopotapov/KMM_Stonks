import SwiftUI
import Stonks

struct ContentView: View {
  let greet = Greeting().greeting()
  @State var error: String = ""
  @State var stocks: [Stock] = []
  
  var body: some View {
    NavigationView {
      List(stocks, id: \.symbol) { stock in
        NavigationLink(destination: StockOverview(symbol: stock.symbol)) {
          StockRow(symbol: stock.symbol, name: stock.displayName, price: stock.ask)
        }
      }
      .navigationTitle("Stonks")
      .navigationBarTitleDisplayMode(.large)
      
    }
    .onAppear(perform: {
      loadStocks()
    })
    
  }
  
  func loadStocks() {
    StonksApi().fetchQuote(completionHandler: { response, error in
      if let error = error { self.error = error.localizedDescription }
      else { self.stocks = response?.quoteResponse.result ?? [] }
    })
  }
}

struct ContentView_Previews: PreviewProvider {
  static var previews: some View {
    ContentView(stocks: [
      Stock(ask: 403.12, displayName: "Apple Inc.", symbol: "AAPL"),
      Stock(ask: 104.43, displayName: "Netflix", symbol: "NTFX"),
      Stock(ask: 30.49, displayName: "McDonald`s", symbol: "MCD")
    ])
  }
}

struct StockRow: View {
  let symbol: String
  let name: String
  let price: Double
  
  var body: some View {
    
    HStack {
      VStack(alignment: .leading) {
        Text(symbol)
        Text(name)
          .font(Font(UIFont.systemFont(ofSize: 15)))
          .foregroundColor(Color.secondary)
      }
      
      Spacer()
      
      Text(String(price) + "$")
        .font(Font(UIFont.italicSystemFont(ofSize: 17)))
      
    }
  }
}

struct StockRow_Previews: PreviewProvider {
  static var previews: some View {
    StockRow(symbol: "AAPL", name: "Apple Inc.", price: 132.42)
  }
}

struct StockOverview: View {
  var symbol: String
  @State var overview: StockDetails?
  
  var body: some View {
    Text(overview?.name ?? "Loading...")
      .onAppear(perform: { loadOverview() })
      .padding()
    
    Text(overview?.description_ ?? "")
      .font(Font(UIFont.systemFont(ofSize: 15)))
      .foregroundColor(.secondary)
      .padding()
    
    Spacer()
  }
  
  func loadOverview() {
    StonksApi().fetchOverview(symbol: symbol, completionHandler: { response, error in
      overview = response
    })
  }
}

struct StockOverview_Previews: PreviewProvider {
  static var previews: some View {
    StockOverview(symbol: "AAPL", overview: StockDetails(name: "Lorem ipsum", description: "Lorem ipsum dolor sit amet"))
  }
}
