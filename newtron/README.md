# Newtron

1. mainファイルにて以下の内容にする
```rust

#![no_std] 
#![no_main]

use core::panic::PanicInfo;

#[no_mangle] 
pub extern "C" fn _start() -> ! {
    loop {}
}
#[panic_handler]
fn panic(_info: &PanicInfo) -> ! {
    loop {}
}
```

2. コンパイルしたコードを実行可能ファイルに紐づけるリンカを参照しようとエラーを出すため、
これを解消する。まず、自分の環境を試しに確認してみる（以下はLinuxの場合）。
```
(base) ubuntu@watcher:~/github/Newtron$ rustc --version --verbose
rustc 1.64.0 (a55dd71d5 2022-09-19)
binary: rustc
commit-hash: a55dd71d5fb0ec5a6a3a9e8c27b2127ba491ce52
commit-date: 2022-09-19
host: x86_64-unknown-linux-gnu
release: 1.64.0
LLVM version: 14.0.6
```
次に環境によらずコンパイルするために、コンパイル後の形式（target）を追加する
```bash
rustup target add thumbv7em-none-eabihf
```
実際に上記のtargetでbuildするとコンパイルすることができる
（runまではできないため、次回行う）
```bash
cargo build --target thumbv7em-none-eabihf
```
