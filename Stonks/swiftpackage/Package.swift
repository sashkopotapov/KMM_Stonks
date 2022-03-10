// swift-tools-version:5.3
import PackageDescription

let package = Package(
    name: "Stonks",
    platforms: [
        .iOS(.v13),
.macOS(.v11)
    ],
    products: [
        .library(
            name: "Stonks",
            targets: ["Stonks"]
        ),
    ],
    targets: [
        .binaryTarget(
            name: "Stonks",
            path: "./Stonks.xcframework"
        ),
    ]
)
