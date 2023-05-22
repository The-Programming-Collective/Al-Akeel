'use client';

import React, { useEffect, useState } from 'react'
import styles from './page.module.css'
import Link from 'next/link';


export default function Page() {
    const [username, setUsername] = useState<any>(localStorage.getItem('username')?.replaceAll('"', ''));
    const [password, setPassword] = useState<any>(localStorage.getItem('password')?.replaceAll('"', ''));
    const [completedOrders, setCompletedOrders] = useState<any>([]);

    // const auth = 'Basic ' + btoa(username + ':' + password);
    const auth = 'Basic eW91c3NlZjp5b3Vzc2Vm';


    useEffect(() => {
        fetch('http://localhost:8080/Al-Akeel/api/runner/completedOrders', {
            method: 'GET',
            headers: {
                accept: '*/*',
                'Content-Type': 'application/json',
                authorization: auth
            },
        })
            .then((response) => response.json())
            .then((data) => {
                setCompletedOrders(data);
            }
            );

    }, []);


    const handleSubmit = (e: any) => {
        e.preventDefault();
    }

    const handleCompleteOrder = (e: any) => {
        e.preventDefault();
        fetch('http://localhost:8080/Al-Akeel/api/runner/completeOrder', {
            method: 'POST',
            headers: {
                accept: '*/*',
                authorization: auth,
                connection: 'keep-alive',
            },
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
            }
            );
    }


    return (
        <main>
            <nav className={styles.center}>
                <Link href="/">
                    Al-Akeel | Home
                </Link>{' '}
            </nav>
            <div className={styles.main}>
                <h1 className={styles.h1}>Runner Account</h1>
                <br></br>
                <h2>Number of completed Orders: {completedOrders}</h2>
                <br></br>
                <button className={styles.submit} onClick={handleCompleteOrder}>Complete Order</button>
            </div>
            <div className={styles.main2}>
                <form className={styles.form}>
                    <label className={styles.label}>Change fee:</label>
                    <br></br>
                    <input className={styles.input} type="text" name="fee" required />
                    <br></br>
                    <button className={styles.submit} type="submit">Submit</button>
                </form>
            </div>
        </main>
    )
}
