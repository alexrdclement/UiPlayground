//
//  ComposeViewController.swift
//  UiPlayground
//
//  Created by Alex Clement on 10/20/24.
//

import SwiftUI
import app

struct MainView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        return MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}
